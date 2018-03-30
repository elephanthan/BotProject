package com.worksmobile.android.botproject;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 3. 30..
 */

public class MessageLab {

    private static MessageLab messageLab;

    private List<Message> messages;

    public static MessageLab get(Context context){
        if(messageLab == null){
            messageLab = new MessageLab(context);

        }
        return messageLab;
    }

    private MessageLab(Context context){
        messages = new ArrayList<>();
        for (int i=0;i<100;i++){
            Message msg = new Message();
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
