package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.feature.chat.chatroom.ChatroomActivity;
import com.worksmobile.android.botproject.feature.chat.newchat.NewchatActivity;
import com.worksmobile.android.botproject.feature.mysetting.MysettingActivity;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.model.User;

import java.util.ArrayList;
import java.util.List;

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
        getMenuInflater().inflate(R.menu.triggers_chatroom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_chatting:
                Chatroom chatroom = new Chatroom();
                List<User> users = new ArrayList<User>();
                for(int i=0;i<5;i++){
                    User user = new User();
                    users.add(user);
                }
                chatroom.setTitle("채팅방#"+(chatrooms.size()+1));
                chatroom.setTumbnail(R.drawable.thumb_default_team);
                Message msg = new Message();
                chatroom.setLatestMsg(msg);
                chatroom.setNumber(users.size());
                chatroom.setParticipants(users);
                chatrooms.add(chatroom);

                chatroomListAdapter.notifyDataSetChanged();

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
        chatrooms = chatroomLab.getChatrooms();

        chatroomListAdapter = new ChatroomListAdapter(this, chatrooms, this) ;
        chatroomRecyclerView.setAdapter(chatroomListAdapter);
    }

    @Override
    public void onHolderClick(int position) {
        startActivity(new Intent(this, ChatroomActivity.class));
    }
}
