package com.worksmobile.android.botproject.feature.chat.chatroom;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 3. 27..
 */
//possessing chatroom list by singleton pattern
public class ChatroomLab {

    private static ChatroomLab chatroomLab;

    private List<Chatroom> chatrooms;

    public static ChatroomLab get(){
        if(chatroomLab == null){
            chatroomLab = new ChatroomLab();

        }
        return chatroomLab;
    }

    private ChatroomLab(){
        chatrooms = new ArrayList<>();
        for (int i=0;i<100;i++){
            Chatroom chatroom = new Chatroom();
            chatroom.setTitle("채팅방#"+(i+1));
            chatroom.setTumbnail(R.drawable.thumb_default_team);
            Message msg = new Message();
            chatroom.setLatestMsg(msg);
            chatroom.setNumber(3);
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
