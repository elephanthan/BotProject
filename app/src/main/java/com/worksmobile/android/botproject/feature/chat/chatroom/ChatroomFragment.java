package com.worksmobile.android.botproject.feature.chat.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.api.MqttRepository;
import com.worksmobile.android.botproject.beacon.RangingFragment;
import com.worksmobile.android.botproject.beacon.SettingInfo;
import com.worksmobile.android.botproject.feature.FullScreenImageActivity;
import com.worksmobile.android.botproject.feature.dialog.UserinfoDialogFragment;
import com.worksmobile.android.botproject.model.Chatbox;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.DropDownMenu;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.util.BeaconUtil;
import com.worksmobile.android.botproject.util.SharedPrefUtil;
import com.worksmobile.android.botproject.util.UnixEpochDateTypeAdapter;
import com.worksmobile.android.botproject.util.ViewUtil;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.worksmobile.android.botproject.feature.chat.chatroom.DropdownMenuDataModel.DROPDOWN_CHATROOM;
import static com.worksmobile.android.botproject.feature.splash.SplashActivity.retrofitClient;

public class ChatroomFragment extends RangingFragment implements ChatroomClickListener, RECORangingListener, RECOServiceConnectListener {

    public static final String TAG = ChatroomFragment.class.getSimpleName();

    private static final String ARG_CHATROOM_ID = "chatroom_id";
    private static final String ARG_CHATROOM_TYPE = "chatroom_type";
    private final int REQUEST_POSITION = 100;
    private final int RESULT_OK = -1;
    int beforeImageClickPosition = -1;
    int chatroomType;

    MqttClient mqttClient;

    @BindView(R.id.button_chatroom_send)
    Button btnChatroomSend;
    @BindView(R.id.edittext_chatroom)
    EditText editTextChatroom;
    @BindView(R.id.indoor_recycler_view)
    RecyclerView messageRecyclerView;

    private View view;
    GridView dropDownView;
    MenuItem showhideMenuItem;

    private MessageAdapter messageAdapter = new MessageAdapter();

    private Chatbox chatbox;
    private List<Message> messages = new ArrayList<>();
    private List<DropDownMenu> dropDownMenus;

    private EndlessRecyclerViewScrollListener scrollListener;

    public static ChatroomFragment newInstance(long chatroomId, int chatroomType) {
        Bundle args = new Bundle();
        args.putLong(ARG_CHATROOM_ID, chatroomId);
        args.putInt(ARG_CHATROOM_TYPE, chatroomType);
        ChatroomFragment fragment = new ChatroomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        long chatroomId = getArguments().getLong(ARG_CHATROOM_ID);
        chatroomType = getArguments().getInt(ARG_CHATROOM_TYPE);

        String employeeNumber = SharedPrefUtil.getStringPreference(getActivity(), SharedPrefUtil.SHAREDPREF_KEY_USERID);
        retrofitClient.getChatbox(chatroomId, employeeNumber, new ApiRepository.RequestChatboxCallback() {

            @Override
            public void success(Chatbox chatbox) {
                ChatroomFragment.this.chatbox = chatbox;
                List<Message> loadedMessags = chatbox.getMsgList();
                Log.i("loaded size", messages.size()+"");
                if(loadedMessags!=null && loadedMessags.size() > 0) {
                    List<Message> typedMessages = messageAdapter.setMessagesByUserId(loadedMessags, employeeNumber);
                    typedMessages = messageAdapter.insertDayMessage(typedMessages);
                    messages.addAll(typedMessages);
                }
                drawFromChatbox();
                setMessageAdapter();
                messageAdapter.setChatBox(chatbox);
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("retrofit error", "Retrofit Error ::: getChatbox" + throwable);
            }
        });

        dropDownMenus = DropdownMenuDataModel.get(DROPDOWN_CHATROOM).getDropDownMenus();

        mqttClient = MqttRepository.getMqttClient(employeeNumber);
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
                Log.i("connectionLost", cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) {
                final Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Date.class, UnixEpochDateTypeAdapter.getUnixEpochDateTypeAdapter())
                        .create();

                final Message arrivedMessage = gson.fromJson(mqttMessage.toString(), Message.class);
                Log.i("messageArrived", arrivedMessage.toString());

                //TODO extract to method
                if(!employeeNumber.equals(arrivedMessage.getSenderId())) {
                    arrivedMessage.setType(Message.VIEW_TYPE_MESSAGE_RECEIVED);
                } else {
                    arrivedMessage.setType(Message.VIEW_TYPE_MESSAGE_SENT);
                }
                messages.add(arrivedMessage);
                getActivity().runOnUiThread(() -> refreshMessage());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                if(token.isComplete()){
                    Log.i("deliveryComplete", "deliveryComplete");
                }
            }
        });

        try {
            mqttClient.connect();
            mqttClient.subscribe(MqttRepository.topic + chatroomId + "/users/" + employeeNumber, MqttRepository.qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        if (chatroomType == Chatroom.CHATROOM_TYPE_BOT) {
            bindRangingListener();
        }
    }

    private void bindRangingListener() {
        mRecoManager.setRangingListener(this);
        mRecoManager.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatroomType == Chatroom.CHATROOM_TYPE_BOT) {
            this.stop(mRegions);
            this.unbind();
        }
    }

    private void unbind() {
        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.i("RECORangingActivity", "Remote Exception");
            e.printStackTrace();
        }
    }

    @Override
    protected void start(ArrayList<RECOBeaconRegion> regions) {
        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.startRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void stop(ArrayList<RECOBeaconRegion> regions) {
        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.stopRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    private void drawFromChatbox() {
        ((ChatroomActivity) getActivity()).getSupportActionBar().setTitle(chatbox.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chatroom, container, false);
        ButterKnife.bind(this, view);

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        createDropDownMenu();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi();
            }
        };
        // Adds the scroll listener to RecyclerView
        messageRecyclerView.addOnScrollListener(scrollListener);

        setScrollPosition();
    }

    private void setScrollPosition() {
        if (beforeImageClickPosition > 0) {
            messageRecyclerView.scrollToPosition(beforeImageClickPosition);
            setBeforeImageClickPosition(-1);
        } else {
            messageRecyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
        }
    }

    public void loadNextDataFromApi() {
        retrofitClient.getMessagesByScroll(chatbox.getChatroomId(), messages.get(0).getId(), 1, new ApiRepository.RequestMessagesCallback() {
            @Override
            public void success(List<Message> messages) {
                if (messages != null && messages.size() > 0) {
                    messages = messageAdapter.insertDayMessage(messages);
                    ChatroomFragment.this.messages.addAll(0, messages);
                    messageAdapter.notifyItemRangeInserted(0, messages.size() - 1);
                    scrollListener.onComplete();
                }
            }

            @Override
            public void error(Throwable throwable) {
                Log.i("retrofit error", "Retrofit Error ::: getMessagesByScroll" + throwable);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_POSITION) {
            if (resultCode == RESULT_OK) {
                int position = intent.getIntExtra("position", messages.size());
                setBeforeImageClickPosition(position);
                //TODO : not working here!
                //messageRecyclerView.scrollToPosition(position);
            } else {

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        showhideMenuItem = menu.getItem(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_showhide:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    item.setIcon(R.drawable.ic_action_arrow_up);
                    item.setTitle(R.string.action_hidemenu);

                    ActionBar actionBar = ((ChatroomActivity)getActivity()).getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setElevation(0);
                    }

                    dropDownView.setVisibility(View.VISIBLE);
                } else {
                    item.setChecked(false);
                    item.setTitle(R.string.action_showmenu);
                    item.setIcon(R.drawable.ic_action_arrow_down);

                    ActionBar actionBar = ((ChatroomActivity)getActivity()).getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setElevation(0);
                    }

                    dropDownView.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
        return false;
    }

    private void createDropDownMenu() {
        DropdownMenuAdapter dropdownMenuAdapter = new DropdownMenuAdapter(getActivity(), dropDownMenus);
        dropDownView = (GridView)view.findViewById(R.id.gridView1);
        dropDownView.setAdapter(dropdownMenuAdapter);
        dropDownView.setOnItemClickListener((parent, view, position, id) -> {
            DropDownMenu dropDownMenu = dropDownMenus.get(position);
            int action = dropDownMenu.getName();
            switch (action) {
                case R.string.action_invite :
                    Toast.makeText(getActivity(), R.string.alert_not_supply, Toast.LENGTH_SHORT).show();
                    break;
                case R.string.action_setnoti :
                    Toast.makeText(getActivity(), R.string.alert_not_supply, Toast.LENGTH_SHORT).show();
                    break;
                case R.string.action_exit :
                    Toast.makeText(getActivity(), R.string.alert_not_supply, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        });
    }

    private void setMessageAdapter() {
        messageAdapter = new MessageAdapter(getActivity(), messages, this);
        messageRecyclerView.setAdapter(messageAdapter);
        if(messages != null && messages.size() > 1) {
            messageRecyclerView.scrollToPosition(messages.size() - 1);
        }
    }

    private void refreshMessage() {
        messageAdapter.notifyDataSetChanged();
        messageRecyclerView.scrollToPosition(messages.size()-1);
    }

    @Override
    public void onMsgClick(int position) {

    }

    @Override
    public void onProfileClick(int position) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Message message = messages.get(position);
        if (message != null && message.getSenderId().length() > 2) {
            UserinfoDialogFragment dialogFragment = UserinfoDialogFragment.newInstance(messages.get(position).getSenderId());
            dialogFragment.show(fm, "fragment_dialog_test");
        }
    }


    @Override
    public void onHolderClick() {
        ViewUtil.hideKeyboardFrom(getContext(), view);
    }

    @Override
    public void onMessageImageClick(int position) {
        Intent intent = FullScreenImageActivity.newIntent(getActivity(), messages.get(position).getText(), position);
        startActivityForResult(intent, REQUEST_POSITION);
    }

    @OnClick(R.id.button_chatroom_send)
    public void onChatroomSendClick() {
        String strText = editTextChatroom.getText().toString();

        if (!strText.equals("")) {
            String employeeNumber = SharedPrefUtil.getStringPreference(getActivity(), SharedPrefUtil.SHAREDPREF_KEY_USERID);

            Message msg = new Message(chatbox.getChatroomId(), strText, employeeNumber);

            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            String msgString = gson.toJson(msg);
            Log.i(TAG, msgString);

            MqttMessage mqttMessage = new MqttMessage(msgString.getBytes());
            mqttMessage.setQos(MqttRepository.qos);

            try {
                if (!mqttClient.isConnected()) {
                    mqttClient.connect();
                }
                mqttClient.publish(MqttRepository.topic + chatbox.getChatroomId(), mqttMessage);
            } catch (MqttException e) {
                e.printStackTrace();
            }

            //TODO : 지금은 MQTT arrive 받고 메시지 그리도록함 향후 보내자마자 그리도록 변경
            //TODO : send 누르자마자 일단 그림! => 그리고 메시지 arrived받으면 그것의 객체를 찾아서 수정해야함..
//            messages.add(msg);
//            refreshMessage();

            editTextChatroom.setText("");
        }
    }

    @OnTouch(R.id.indoor_recycler_view)
    public boolean onRecyclerViewTouch() {
        ViewUtil.hideKeyboardFrom(getContext(), view);
        return false;
    }

    public void setBeforeImageClickPosition(int beforeImageClickPosition) {
        this.beforeImageClickPosition = beforeImageClickPosition;
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> beacons, RECOBeaconRegion region) {
        Log.i(TAG, "didRangeBeaconsInRegion() region: " + region.getUniqueIdentifier() + ", number of beacons ranged: " + beacons.size());

        String employeeNumber = SharedPrefUtil.getStringPreference(getActivity(), SharedPrefUtil.SHAREDPREF_KEY_USERID);

        if (beacons.size() == 0){
            return;
        }


        if(!BeaconUtil.checkIsSentRecently(getActivity(), region)) {
            for (RECOBeacon beacon : beacons) {
                retrofitClient.sendBeaconEvent(employeeNumber, beacon.getProximityUuid(), beacon.getMajor(), beacon.getMinor(), 1, beacon.getAccuracy(), new ApiRepository.RequestVoidCallback() {
                    @Override
                    public void success() {
                        BeaconUtil.popupNotification(getActivity(), region.getUniqueIdentifier() + "입장");
                    }

                    @Override
                    public void error(Throwable throwable) {

                    }
                });
            }
        }
    }

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion recoBeaconRegion, RECOErrorCode recoErrorCode) {
        return;
    }

    @Override
    public void onServiceConnect() {
        if (chatroomType == Chatroom.CHATROOM_TYPE_BOT) {
            Log.i(TAG, "onServiceConnect()");
            mRecoManager.setDiscontinuousScan(SettingInfo.DISCONTINUOUS_SCAN);
            this.start(mRegions);
        }
    }

    @Override
    public void onServiceFail(RECOErrorCode recoErrorCode) {
        return;
    }
}
