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

import static com.worksmobile.android.botproject.feature.splash.SplashActivity.retrofitClient;

public class ChatroomListActivity extends AppCompatActivity implements ChatroomListContract.View {

    private ChatroomListContract.Presenter chatroomListPresenter;

    private View chatroomListView;
    private View noChatroomListView;
    private RecyclerView chatroomRecyclerView;

    List<Chatroom> chatrooms = new ArrayList<Chatroom>();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;

    ChatroomListContract.AdapterView adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroomlist);
        getSupportActionBar().setTitle(R.string.barname_chatroomlist);

        chatroomListView = (View) findViewById(R.id.layout_chatlist);
        noChatroomListView = (View) findViewById(R.id.no_chatroom_layout);
        chatroomRecyclerView = (RecyclerView) findViewById(R.id.chat_room_recycler_view);
        chatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        ChatroomListAdapter chatroomListAdapter = new ChatroomListAdapter(this);
        chatroomRecyclerView.setAdapter(chatroomListAdapter);
        adapterView = chatroomListAdapter;
        chatroomListPresenter = new ChatroomListPresenter(this, adapterView, chatroomListAdapter);

        String employeeNumber = SharedPrefUtil.getStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID);
        chatroomListPresenter.loadChatrooms(employeeNumber);

        chatroomListAdapter.setOnRecyclerItemClickListener((position) -> {
            chatroomListPresenter.enterChatroom(position);
        });

        //updateUI();

        //TODO restore code when finished multi user test
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
                .setAction(R.string.command_OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(ChatroomListActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, SettingInfo.REQUEST_LOCATION);
                    }
                })
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
    public void moveToChatroom(long chatroomId) {
        Intent intent = ChatroomActivity.newIntent(this, chatroomId);
        startActivity(intent);
    }
}
