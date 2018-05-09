package com.worksmobile.android.botproject.feature.chat.chatroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.MqttRepository;
import com.worksmobile.android.botproject.feature.chat.chatroomlist.ChatroomLab;
import com.worksmobile.android.botproject.feature.chat.chatroomlist.ChatroomListActivity;
import com.worksmobile.android.botproject.feature.chat.newchat.NewchatActivity;
import com.worksmobile.android.botproject.feature.dialog.SetnotiDialogFragment;
import com.worksmobile.android.botproject.feature.dialog.UserinfoDialogFragment;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.DropDownMenu;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.util.ViewUtil;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.worksmobile.android.botproject.feature.chat.chatroom.DropdownMenuLab.DROPDOWN_CHATROOM;

public class ChatroomFragment extends Fragment implements ChatroomClickListener {

    private static final String ARG_CHATROOM_ID = "chatroom_id";

    @BindView(R.id.button_chatroom_send)
    Button btnChatroomSend;
    @BindView(R.id.edittext_chatroom)
    EditText editTextChatroom;
    @BindView(R.id.indoor_recycler_view)
    RecyclerView messageRecyclerView;

    private View view;

    private MessageAdapter messageAdapter;

    private Chatroom chatroom;
    private List<Message> messages;
    private List<DropDownMenu> dropDownMenus;

    MenuItem showhideMenuItem;

    GridView dropDownView;

    private MqttClient mqttClient;
    Gson gson;

    public static ChatroomFragment newInstance(long chatroomId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CHATROOM_ID, chatroomId);
        ChatroomFragment fragment = new ChatroomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        long chatroomId = (long) getArguments().getLong(ARG_CHATROOM_ID);
        chatroom = ChatroomLab.get().getChatroom(chatroomId);

        //TODO 채팅방 아이디로 메시지 내역들을 가져옴
        messages = MessageLab.get().getMessages();
        dropDownMenus = DropdownMenuLab.get(DROPDOWN_CHATROOM).getDropDownMenus();

        SharedPreferences sharedPref =  getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String employeeNumber = sharedPref.getString("employee_number", "WM060001");

        mqttClient = MqttRepository.getMqttClient();
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
                Log.i("connectionLost", cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                Log.i("messageArrived", message.toString() + "|||" + topic);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                try {
                    if(token.isComplete()){
                        Log.i("ee", "ee?");
                    }
                    MqttMessage msg = token.getMessage();
                    Log.i("deliveryComplete", "msg");

                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
        });

        try {
            mqttClient.connect();
            Log.d("subtopic", MqttRepository.topic + chatroomId + "/users/" + employeeNumber);
            mqttClient.subscribe(MqttRepository.topic + chatroomId + "/users/" + employeeNumber, MqttRepository.qos);
//            mqttClient.subscribe("#", MqttRepository.qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ChatroomActivity) getActivity()).getSupportActionBar().setTitle(chatroom.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chatroom, container, false);
        ButterKnife.bind(this, view);

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        createDropDownMenu();
        updateIndoorUI();

        return view;
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
                    ((ChatroomActivity)getActivity()).getSupportActionBar().setElevation(0);
                    dropDownView.setVisibility(View.VISIBLE);
                } else {
                    item.setChecked(false);
                    item.setTitle(R.string.action_showmenu);
                    item.setIcon(R.drawable.ic_action_arrow_down);
                    ((ChatroomActivity)getActivity()).getSupportActionBar().setElevation(8);
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
        dropDownView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DropDownMenu dropDownMenu = dropDownMenus.get(position);
                int action = dropDownMenu.getName();
                switch (action) {
                    case R.string.action_invite :
                        long chatroomId = (long) getArguments().getLong(ARG_CHATROOM_ID);
                        Intent intent = new Intent(getActivity(), NewchatActivity.class);
                        intent.putExtra(ARG_CHATROOM_ID, chatroomId);
                        startActivity(intent);
                        break;
                    case R.string.action_setnoti :
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        SharedPreferences sharedPref =  getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                        String employeeNumber = sharedPref.getString("employee_number", "WM060001");

                        SetnotiDialogFragment dialogFragment = SetnotiDialogFragment.newInstance(employeeNumber);

                        dialogFragment.show(fm, "fragment_dialog_test");
                        break;
                    case R.string.action_exit :
                        //TODO Call API Server Exit Room
                        startActivity(new Intent(getActivity(), ChatroomListActivity.class));
                        break;
                    default:

                }
            }
        });
    }

    private void updateIndoorUI() {
        MessageLab messageLab = MessageLab.get();
        List<Message> messages = messageLab.getMessages();

        if (messageAdapter == null) {
            messageAdapter = new MessageAdapter(getActivity(), messages, this);
            messageRecyclerView.setAdapter(messageAdapter);
        } else {

        }
    }

    @Override
    public void onMsgClick(int position) {
        Toast.makeText(getActivity(), messages.get(position).getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileClick(int position) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        UserinfoDialogFragment dialogFragment = UserinfoDialogFragment.newInstance(messages.get(position).getSenderId());
        dialogFragment.show(fm, "fragment_dialog_test");
    }


    @Override
    public void onHolderClick() {
        ViewUtil.hideKeyboardFrom(getContext(), view);
    }

    @OnClick(R.id.button_chatroom_send)
    public void onChatroomSendClick() {
        String strText = editTextChatroom.getText().toString();


        if (!strText.equals("")) {
            //Message msg = new Message(strText, Message.VIEW_TYPE_MESSAGE_SENT);
            Message msg = new Message(strText, messages.size() % 2, "WM060001");

            String msgString = gson.toJson(msg);

            Log.d("SendMsg", msgString);

            MqttMessage mqttMessage = new MqttMessage(msgString.getBytes());
            mqttMessage.setQos(MqttRepository.qos);
            Log.d("pubtopic", MqttRepository.topic + chatroom.getId());
            try {
                mqttClient.publish(MqttRepository.topic + chatroom.getId(), mqttMessage);
            } catch (MqttException e) {
                e.printStackTrace();
            }

            messages.add(msg);
            messageAdapter.notifyDataSetChanged();

            editTextChatroom.setText("");
            messageRecyclerView.smoothScrollToPosition(messages.size());
        }
    }

    @OnTouch(R.id.indoor_recycler_view)
    public boolean onRecyclerViewTouch() {
        ViewUtil.hideKeyboardFrom(getContext(), view);
        return false;
    }

}
