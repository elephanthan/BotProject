package com.worksmobile.android.botproject;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 3. 27..
 */

public class ChatroomLab {

    private static ChatroomLab sChatroomLab;

    private List<Chatroom> mChatrooms;

    public static ChatroomLab get(Context context){
        if(sChatroomLab == null){
            sChatroomLab = new ChatroomLab(context);

        }
        return sChatroomLab;
    }

    private ChatroomLab(Context context){
        mChatrooms = new ArrayList<>();
        for (int i=0;i<100;i++){
            Chatroom chatroom = new Chatroom();
            chatroom.setTitle("채팅방#"+(i+1));
            chatroom.setTumbnail(R.drawable.thumb_default_team);
            mChatrooms.add(chatroom);
        }
    }

    public List<Chatroom> getChatrooms(){
        return mChatrooms;
    }

    public Chatroom getChatroom(long id){
        for(Chatroom chatroom : mChatrooms){
            if(chatroom.getId() == id){
                return chatroom;
            }
        }
        return null;
    }

}
