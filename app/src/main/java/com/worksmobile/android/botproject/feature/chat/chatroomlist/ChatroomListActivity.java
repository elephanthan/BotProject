package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.beacon.SettingInfo;
import com.worksmobile.android.botproject.feature.chat.chatroom.ChatroomActivity;
import com.worksmobile.android.botproject.feature.chat.newchat.NewchatActivity;
import com.worksmobile.android.botproject.feature.mysetting.MysettingActivity;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.worksmobile.android.botproject.feature.splash.SplashActivity.retrofitClient;

public class ChatroomListActivity extends AppCompatActivity implements ChatroomListContract.View {
    public static final String TAG = ChatroomListActivity.class.getSimpleName();

    public static final int REQUEST_NEWCHAT = 100;

    @Inject
    ChatroomListContract.Presenter chatroomListPresenter;

    View chatroomListView;
    private View noChatroomListView;
    private RecyclerView chatroomRecyclerView;

    List<Chatroom> chatrooms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroomlist);

//        AndroidInjection.inject(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            getSupportActionBar().setTitle(R.string.barname_chatroomlist);
        }

        chatroomListView = findViewById(R.id.layout_chatlist);
        noChatroomListView = findViewById(R.id.no_chatroom_layout);
        chatroomRecyclerView =  findViewById(R.id.chat_room_recycler_view);
        chatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        ChatroomListAdapter chatroomListAdapter = new ChatroomListAdapter(this);
        chatroomRecyclerView.setAdapter(chatroomListAdapter);
//        chatroomListPresenter = new ChatroomListPresenter(this, chatroomListAdapter, chatroomListAdapter);

        String employeeNumber = SharedPrefUtil.getStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID);
        chatroomListPresenter.loadChatrooms(employeeNumber);

        chatroomListAdapter.setOnRecyclerItemClickListener((position) -> {
            chatroomListPresenter.enterChatroom(position);
        });

        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();

        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, SettingInfo.REQUEST_ENABLE_BT);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is not granted.");
                requestLocationPermission();
            } else {
                Log.i(TAG, "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is already granted.");
            }
        }
    }


    @Override
    public void onResume() {
        String employeeNumber = SharedPrefUtil.getStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID);
        chatroomListPresenter.loadChatrooms(employeeNumber);

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SettingInfo.REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        if (requestCode == REQUEST_NEWCHAT) {
            if (resultCode == RESULT_OK) {
                long chatroomId = intent.getLongExtra("chatroom_id", -1);
                int chatroomType = intent.getIntExtra("chatroom_type", Chatroom.CHATROOM_TYPE_USER);
                if(chatroomId > 0) {
                    String employeeNumber = SharedPrefUtil.getStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID);
                    chatroomListPresenter.loadChatrooms(employeeNumber);

                    moveToChatroom(chatroomId, chatroomType);
                }
            } else {

            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.triggers_chatroomlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_chatting:
                Intent intent = new Intent(this, NewchatActivity.class);
                startActivityForResult(intent, REQUEST_NEWCHAT);
                return true;
            case R.id.menu_item_my_setting:
                startActivity(new Intent(this, MysettingActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateUI(){
        String employeeNumber = SharedPrefUtil.getStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID);
        retrofitClient.getChatroomList(employeeNumber, new ApiRepository.RequestChatroomListCallback() {
            @Override
            public void success(List<Chatroom> chatroomList) {
                Log.d("retrofit Success", chatroomList.toString());
                chatrooms = chatroomList;
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("retrofit error", "Retrofit Error ::: loginUser");
            }
        });
    }

    private void requestLocationPermission() {
        if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, SettingInfo.REQUEST_LOCATION);
            return;
        }

        Snackbar.make(chatroomListView, R.string.location_permission_rationale, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.command_OK, v -> ActivityCompat.requestPermissions(ChatroomListActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, SettingInfo.REQUEST_LOCATION))
                .show();
    }

    @Override
    public void setChatroomListPresenter(ChatroomListContract.Presenter chatroomListPresenter) {
        this.chatroomListPresenter = chatroomListPresenter;
    }

    @Override
    public void showChatrooms() {
        chatroomRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoChatrooms() {
        chatroomRecyclerView.setVisibility(View.GONE);
        noChatroomListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void moveToChatroom(long chatroomId, int chatroomType) {
        Intent intent = ChatroomActivity.newIntent(this, chatroomId, chatroomType);
        startActivity(intent);
    }

}
