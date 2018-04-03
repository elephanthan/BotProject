package com.worksmobile.android.botproject.view.Chat.Chatroom;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.worksmobile.android.botproject.view.SingleFragmentActivity;

public class ChatroomActivity extends SingleFragmentActivity {

    private static final String EXTRA_CHATROOM_ID =
            "chatroom_id";

    public static Intent newIntent(Context context, long chatroomId){
        Intent intent = new Intent(context, ChatroomActivity.class);
        intent.putExtra(EXTRA_CHATROOM_ID, chatroomId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        long chatroomId = (long) getIntent().getLongExtra(EXTRA_CHATROOM_ID, 0);
        return ChatroomFragment.newInstance(chatroomId);
    }
}
