package com.worksmobile.android.botproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import java.util.List;

public class ChatroomListActivity extends AppCompatActivity {

    private RecyclerView chatroomRecyclerView;
    private ChatroomListAdapter adapter;

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

    private void updateUI(){
        ChatroomLab chatroomLab = ChatroomLab.get(getApplicationContext());
        List<Chatroom> chatrooms = chatroomLab.getChatrooms();

        adapter = new ChatroomListAdapter(this, chatrooms) ;
        chatroomRecyclerView.setAdapter(adapter);
    }

}
