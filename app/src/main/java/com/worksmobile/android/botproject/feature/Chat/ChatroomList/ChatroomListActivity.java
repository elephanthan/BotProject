package com.worksmobile.android.botproject.feature.chat.ChatroomList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.api.ApiRepository.RequestCallback;
import com.worksmobile.android.botproject.api.HttpUrlConnectionClient;
import com.worksmobile.android.botproject.api.RetrofitClient;
import com.worksmobile.android.botproject.feature.chat.chatroom.ChatroomLab;
import com.worksmobile.android.botproject.feature.chat.newchat.NewChattingActivity;
import com.worksmobile.android.botproject.feature.mysetting.MySettingActivity;
import com.worksmobile.android.botproject.model.Chatroom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatroomListActivity extends AppCompatActivity {

    private RecyclerView chatroomRecyclerView;
    private ChatroomListAdapter adapter;
    //private RetrofitClient.ApiService apiService;
    ApiRepository urlConnection, retrofit;

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
        getMenuInflater().inflate(R.menu.triggers_chatroom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_chatting:
                //Toast.makeText(getApplicationContext(),"New Chatting Menu item was selected.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, NewChattingActivity.class));
                return true;
            case R.id.menu_item_my_setting:
                //Toast.makeText(getApplicationContext(),"My Setting Menu item was selected.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MySettingActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateUI(){
        ChatroomLab chatroomLab = ChatroomLab.get();
        List<Chatroom> chatrooms = chatroomLab.getChatrooms();

        adapter = new ChatroomListAdapter(this, chatrooms) ;
        chatroomRecyclerView.setAdapter(adapter);

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
        map1.put("PATH", "/comments");
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

    }


}
