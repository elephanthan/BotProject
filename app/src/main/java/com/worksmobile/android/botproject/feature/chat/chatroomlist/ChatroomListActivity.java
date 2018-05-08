package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.beacon.SettingInfo;
import com.worksmobile.android.botproject.feature.chat.chatroom.ChatroomActivity;
import com.worksmobile.android.botproject.feature.chat.newchat.NewchatActivity;
import com.worksmobile.android.botproject.feature.mysetting.MysettingActivity;
import com.worksmobile.android.botproject.model.Chatroom;

import java.util.ArrayList;
import java.util.List;

import static com.worksmobile.android.botproject.feature.login.LoginActivity.retrofitClient;

public class ChatroomListActivity extends AppCompatActivity implements ChatroomListClickListener {

    private View chatroomLayout;
    private RecyclerView chatroomRecyclerView;

    private ChatroomListAdapter chatroomListAdapter;
    List<Chatroom> chatrooms = new ArrayList<Chatroom>();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chatroom_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleTextView = (TextView) findViewById(R.id.toolbar_title);
        titleTextView.setText(R.string.barname_chatroomlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.toolbar_common);

        chatroomRecyclerView = (RecyclerView) findViewById(R.id.chat_room_recycler_view);
        chatroomLayout = (View) findViewById(R.id.layout_chatlist);
        chatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();

        //If a user device turns off bluetooth, request to turn it on.
        //사용자가 블루투스를 켜도록 요청합니다.
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, SettingInfo.REQUEST_ENABLE_BT);
        }

        /**
         * In order to use RECO SDK for Android API 23 (Marshmallow) or higher,
         * the location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is required.
         * Please refer to the following permission guide and sample code provided by Google.
         *
         * 안드로이드 API 23 (마시멜로우)이상 버전부터, 정상적으로 RECO SDK를 사용하기 위해서는
         * 위치 권한 (ACCESS_COARSE_LOCATION 혹은 ACCESS_FINE_LOCATION)을 요청해야 합니다.
         * 권한 요청의 경우, 구글에서 제공하는 가이드를 참고하시기 바랍니다.
         *
         * http://www.google.com/design/spec/patterns/permissions.html
         * https://github.com/googlesamples/android-RuntimePermissions
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is not granted.");
                requestLocationPermission();
            } else {
                Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is already granted.");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SettingInfo.REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            //If the request to turn on bluetooth is denied, the app will be finished.
            //사용자가 블루투스 요청을 허용하지 않았을 경우, 어플리케이션은 종료됩니다.
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
//                Chatroom chatroom = new Chatroom();
//                UserLab userLab = UserLab.get();
//                List<User> users = userLab.getUsers();
//                chatroom.setTitle("채팅방#"+(chatrooms.size()+1));
//                chatroom.setTumbnail(R.drawable.thumb_default_team);
//                Message msg = new Message();
////                chatroom.setLatestMsg(msg);
//                chatroom.setLastMessageContent();
//                chatroom.setNumber(users.size());
////                chatroom.setParticipants(users);
//                chatrooms.add(chatroom);

                startActivity(new Intent(this, NewchatActivity.class));
                return true;
            case R.id.menu_item_my_setting:
                startActivity(new Intent(this, MysettingActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateUI(){
        SharedPreferences sharedPref =  getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String employeeNumber = sharedPref.getString("employee_number", "WM060001");
        retrofitClient.getChatroomList(employeeNumber, new ApiRepository.RequestChatroomListCallback() {
            @Override
            public void success(List<Chatroom> chatroomList) {
                Log.d("retrofit Success", chatroomList.toString());
                chatrooms = chatroomList;
                onLoadGetChatrooms();
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("retrofit error", "Retrofit Error ::: loginUser");
                onLoadGetChatrooms();
            }
        });
    }

    private void onLoadGetChatrooms(){
        chatroomListAdapter = new ChatroomListAdapter(this, chatrooms, this) ;
        chatroomRecyclerView.setAdapter(chatroomListAdapter);
    }

    @Override
    public void onHolderClick(int position) {
        Intent intent = ChatroomActivity.newIntent(this, chatrooms.get(position).getId());
        startActivity(intent);
    }

    private void requestLocationPermission() {
        if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, SettingInfo.REQUEST_LOCATION);
            return;
        }

        Snackbar.make(chatroomLayout, R.string.location_permission_rationale, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.command_OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(ChatroomListActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, SettingInfo.REQUEST_LOCATION);
                    }
                })
                .show();
    }
}
