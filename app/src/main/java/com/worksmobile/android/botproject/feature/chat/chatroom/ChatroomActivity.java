package com.worksmobile.android.botproject.feature.chat.chatroom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.feature.SingleFragmentActivity;
import com.worksmobile.android.botproject.model.Chatroom;

public class ChatroomActivity extends SingleFragmentActivity {

    private static final String EXTRA_CHATROOM_ID =  "chatroom_id";
    private static final String EXTRA_CHATROOM_TYPE = "chatroom_type";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Intent newIntent(Context context, long chatroomId, int chatroomType){
        Intent intent = new Intent(context, ChatroomActivity.class);
        intent.putExtra(EXTRA_CHATROOM_ID, chatroomId);
        intent.putExtra(EXTRA_CHATROOM_TYPE, chatroomType);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.triggers_chatroom, menu);
        return true;
    }

    @Override
    protected Fragment createFragment() {
        long chatroomId = getIntent().getLongExtra(EXTRA_CHATROOM_ID, 0);
        int chatroomType = getIntent().getIntExtra(EXTRA_CHATROOM_TYPE, Chatroom.CHATROOM_TYPE_USER);
        return ChatroomFragment.newInstance(chatroomId, chatroomType);
    }

}
