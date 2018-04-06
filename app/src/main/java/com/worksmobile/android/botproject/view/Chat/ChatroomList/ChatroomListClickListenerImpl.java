package com.worksmobile.android.botproject.view.Chat.ChatroomList;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.view.Chat.Chatroom.ChatroomActivity;

/**
 * Created by user on 2018. 4. 4..
 */

public class ChatroomListClickListenerImpl implements ChatroomListClickListener {
    Context context;
    Chatroom chatroom;

    public ChatroomListClickListenerImpl(Context context, Chatroom chatroom){
        this.context = context;
        this.chatroom = chatroom;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_chatroom_item :
                Intent intent = ChatroomActivity.newIntent(context, chatroom.getId());
                context.startActivity(intent);
                break;
            default:
                break;
        }
    }
}
