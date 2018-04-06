package com.worksmobile.android.botproject.view.Chat.ChatroomList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.worksmobile.android.botproject.view.MySetting.MySettingActivity;
import com.worksmobile.android.botproject.view.Chat.NewChatting.NewChattingActivity;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.view.Chat.Chatroom.ChatroomLab;

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
    }

}