package com.worksmobile.android.botproject.feature.chat.chatroom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.worksmobile.android.botproject.feature.SingleFragmentActivity;

public class ChatroomActivity extends SingleFragmentActivity {

    private ViewPager viewPager;
    private View mainView;

    private static final String EXTRA_CHATROOM_ID =
            "chatroom_id";

    public static Intent newIntent(Context context, long chatroomId){
        Intent intent = new Intent(context, ChatroomActivity.class);
        intent.putExtra(EXTRA_CHATROOM_ID, chatroomId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        long chatroomId = (long) getIntent().getLongExtra(EXTRA_CHATROOM_ID, 0);
        return ChatroomFragment.newInstance(chatroomId);
    }

}
