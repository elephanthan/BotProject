package com.worksmobile.android.botproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class ChatroomListActivity extends AppCompatActivity {

    private RecyclerView mChatroomRecyclerView;
    private ChatroomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        mChatroomRecyclerView = (RecyclerView) findViewById(R.id.chat_room_recycler_view);
        mChatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();
    }

    private void updateUI(){
        ChatroomLab chatroomLab = ChatroomLab.get(getApplicationContext());
        List<Chatroom> chatrooms = chatroomLab.getChatrooms();

        adapter = new ChatroomAdapter(this, R.layout.list_item_chatroom, chatrooms) ;
        mChatroomRecyclerView.setAdapter(adapter);
    }

}
