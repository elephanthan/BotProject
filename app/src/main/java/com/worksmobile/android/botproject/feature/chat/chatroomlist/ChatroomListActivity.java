package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.RetrofitClient;
import com.worksmobile.android.botproject.feature.chat.chatroom.ChatroomActivity;
import com.worksmobile.android.botproject.feature.chat.newchat.NewchatActivity;
import com.worksmobile.android.botproject.feature.mysetting.MysettingActivity;
import com.worksmobile.android.botproject.model.Chatroom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatroomListActivity extends AppCompatActivity implements ChatroomListClickListener {

    private RecyclerView chatroomRecyclerView;
    private RetrofitClient retrofit;

    private ChatroomListAdapter chatroomListAdapter;
    List<Chatroom> chatrooms = new ArrayList<Chatroom>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        chatroomRecyclerView = (RecyclerView) findViewById(R.id.chat_room_recycler_view);
        chatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        urlConnection = new HttpUrlConnectionClient();
//        retrofit = new RetrofitClient();

        updateUI();
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
        ChatroomLab chatroomLab = ChatroomLab.get();
        chatrooms = chatroomLab.getChatrooms();

        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("PATH", "/comments");
        map1.put("key", "postId");
        map1.put("value", "1");

        chatroomListAdapter = new ChatroomListAdapter(this, chatrooms, this) ;
        chatroomRecyclerView.setAdapter(chatroomListAdapter);
    }

    @Override
    public void onHolderClick(int position) {
        startActivity(new Intent(this, ChatroomActivity.class));
    }
}
