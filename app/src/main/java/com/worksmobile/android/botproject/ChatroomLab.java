package com.worksmobile.android.botproject;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 3. 27..
 */
//possessing chatroom list by singleton pattern
public class ChatroomLab {

    private static ChatroomLab chatroomLab;

    private List<Chatroom> chatrooms;

    public static ChatroomLab get(Context context){
        if(chatroomLab == null){
            chatroomLab = new ChatroomLab(context);

        }
        return chatroomLab;
    }

    private ChatroomLab(Context context){
        chatrooms = new ArrayList<>();
        for (int i=0;i<100;i++){
            Chatroom chatroom = new Chatroom();
            chatroom.setTitle("채팅방#"+(i+1));
            chatroom.setTumbnail(R.drawable.thumb_default_team);
            chatrooms.add(chatroom);
        }
    }

    public List<Chatroom> getChatrooms(){
        return chatrooms;
    }

    public Chatroom getChatroom(long id){
        for(Chatroom chatroom : chatrooms){
            if(chatroom.getId() == id){
                return chatroom;
            }
        }
        return new Chatroom();
    }

}