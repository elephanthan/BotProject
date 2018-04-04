package com.worksmobile.android.botproject.view.Chat.Chatroom;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.worksmobile.android.botproject.model.Message;

import java.util.List;

/**
 * Created by user on 2018. 4. 4..
 */

class ChatroomClickListnerImpl implements ChatroomClickListner {

    Context context;
    List<Message> messages;


    public ChatroomClickListnerImpl(Context context, List<Message> messages){
        this.context = context;
        this.messages = messages;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, "aaa", Toast.LENGTH_SHORT).show();
//        switch (view.getId()){
//            case R.id.image_message_profile :
//                //Toast.makeText(context, messages.get(getAdapterPosition()).getText(), Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//        }
    }
}
