package com.worksmobile.android.botproject.feature.chat.chatroom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.feature.SingleFragmentActivity;

public class ChatroomActivity extends SingleFragmentActivity {

    private static final String EXTRA_CHATROOM_ID =
            "chatroom_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Intent newIntent(Context context, long chatroomId){
        Intent intent = new Intent(context, ChatroomActivity.class);
        intent.putExtra(EXTRA_CHATROOM_ID, chatroomId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        long chatroomId = (long) getIntent().getLongExtra(EXTRA_CHATROOM_ID, 0);
        findViewById(R.id.fragment_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "test$$", Toast.LENGTH_SHORT).show();
            }
        });

        return ChatroomFragment.newInstance(chatroomId);
    }

}
