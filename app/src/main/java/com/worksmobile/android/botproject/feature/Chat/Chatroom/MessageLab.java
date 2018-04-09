package com.worksmobile.android.botproject.feature.Chat.Chatroom;

import com.worksmobile.android.botproject.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 3. 30..
 */

public class MessageLab {

    private static MessageLab messageLab;

    private List<Message> messages;

    public static MessageLab get(){
        if(messageLab == null){
            messageLab = new MessageLab();

        }
        return messageLab;
    }

    private MessageLab(){
        messages = new ArrayList<>();
        for (int i=0;i<100;i++){
            Message msg = new Message(i+1);
            messages.add(msg);
        }
    }

    public List<Message> getMessages(){
        return messages;
    }

    public Message getMessage(long id){
        for(Message msg : messages){
            if(msg.getId() == id){
                return msg;
            }
        }
        return new Message();
    }

}
