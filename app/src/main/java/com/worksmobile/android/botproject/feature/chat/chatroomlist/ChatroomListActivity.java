package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.feature.chat.chatroom.ChatroomActivity;
import com.worksmobile.android.botproject.feature.chat.newchat.NewchatActivity;
import com.worksmobile.android.botproject.feature.mysetting.MysettingActivity;
import com.worksmobile.android.botproject.model.Chatroom;

import java.util.ArrayList;
import java.util.List;

import static com.worksmobile.android.botproject.feature.login.LoginActivity.retrofitClient;

public class ChatroomListActivity extends AppCompatActivity implements ChatroomListClickListener {

    private RecyclerView chatroomRecyclerView;

    private ChatroomListAdapter chatroomListAdapter;
    List<Chatroom> chatrooms = new ArrayList<Chatroom>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        chatroomRecyclerView = (RecyclerView) findViewById(R.id.chat_room_recycler_view);
        chatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getSupportActionBar().setTitle(R.string.barname_chatroomlist);
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
        ChatroomLab chatroomLab = ChatroomLab.get();
        //chatrooms = chatroomLab.getChatrooms();

        final Context context = this;
        final ChatroomListClickListener listener = this;

        SharedPreferences sharedPref =  getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String employeeNumber = sharedPref.getString("employee_number", "-1");
        retrofitClient.getChatroomList(employeeNumber, new ApiRepository.RequestChatroomListCallback() {
            @Override
            public void success(List<Chatroom> chatrooms_) {
                Log.d("retrofit Success", chatrooms_.toString());
                chatrooms = chatrooms_;
                chatroomListAdapter = new ChatroomListAdapter(context, chatrooms, listener) ;
                chatroomRecyclerView.setAdapter(chatroomListAdapter);
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("retrofit error", "Retrofit Error ::: loginUser");
                chatroomListAdapter = new ChatroomListAdapter(context, chatrooms, listener) ;
                chatroomRecyclerView.setAdapter(chatroomListAdapter);
            }
        });

//        chatroomListAdapter = new ChatroomListAdapter(this, chatrooms, this) ;
//        chatroomRecyclerView.setAdapter(chatroomListAdapter);
    }

    @Override
    public void onHolderClick(int position) {
        Intent intent = ChatroomActivity.newIntent(this, chatrooms.get(position).getId());
        startActivity(intent);
    }
}
