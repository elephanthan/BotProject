package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository.RequestCallback;
import com.worksmobile.android.botproject.api.HttpUrlConnectionClient;
import com.worksmobile.android.botproject.api.RetrofitClient;
import com.worksmobile.android.botproject.feature.chat.chatroom.ChatroomActivity;
import com.worksmobile.android.botproject.feature.chat.chatroom.UserLab;
import com.worksmobile.android.botproject.feature.chat.newchat.NewchatActivity;
import com.worksmobile.android.botproject.feature.mysetting.MysettingActivity;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatroomListActivity extends AppCompatActivity implements ChatroomListClickListener {

    private RecyclerView chatroomRecyclerView;
    private HttpUrlConnectionClient urlConnection;
    private RetrofitClient retrofit;

    private ChatroomListAdapter chatroomListAdapter;
    List<Chatroom> chatrooms = new ArrayList<Chatroom>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        chatroomRecyclerView = (RecyclerView) findViewById(R.id.chat_room_recycler_view);
        chatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        urlConnection = new HttpUrlConnectionClient();
        retrofit = new RetrofitClient();

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
                Chatroom chatroom = new Chatroom();
                UserLab userLab = UserLab.get();
                List<User> users = userLab.getUsers();
                chatroom.setTitle("채팅방#"+(chatrooms.size()+1));
                chatroom.setTumbnail(R.drawable.thumb_default_team);
                Message msg = new Message();
                chatroom.setLatestMsg(msg);
                chatroom.setNumber(users.size());
                chatroom.setParticipants(users);
                chatrooms.add(chatroom);

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

        urlConnection.getComment(map1, new RequestCallback() {
            @Override
            public void success(List<Object> comments) {
                Log.d("url comment Success", comments.toString());
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("url comment error", "hoyahoya");
            }
        });

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("PATH", "/posts");

        urlConnection.getPosts(map2, new RequestCallback() {
            @Override
            public void success(List<Object> posts) {
                Log.d("url post Success", posts.toString());
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("url post error", "hoyahoya");
            }
        });

        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("key", "id");
        map3.put("value","1");

        retrofit.getComment(map3, new RequestCallback() {
            @Override
            public void success(List<Object> comments) {
                Log.d("retrofit Success", comments.toString());
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("retrofit error", "hoyahoya");
            }
        });


        chatroomListAdapter = new ChatroomListAdapter(this, chatrooms, this) ;
        chatroomRecyclerView.setAdapter(chatroomListAdapter);

    }

    @Override
    public void onHolderClick(int position) {
        startActivity(new Intent(this, ChatroomActivity.class));
    }
}
