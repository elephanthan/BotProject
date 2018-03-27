package com.worksmobile.android.botproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatroomListActivity extends AppCompatActivity {

    ArrayList<Chatroom> chatrooms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        ListView listView=(ListView)findViewById(R.id.chat_room_listview);


        for(int i=0;i<10;i++) {
            Chatroom chatroom = new Chatroom("채팅방#"+(i+1), R.drawable.thumb_default_team);
            chatrooms.add(chatroom);
        }

        ChatroomListAdapter adapter=new ChatroomListAdapter(this,R.layout.list_item_chatroom, chatrooms);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChatroomListActivity.this, chatrooms.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
