package com.worksmobile.android.botproject.model;

import com.worksmobile.android.botproject.util.CommonUtil;

import java.util.Date;

/**
 * Created by user on 2018. 3. 29..
 */

public class Message {
    private long id;
    private String senderId;
    private String text;
    private Date senddate;
    private int type;

    public static final int VIEW_TYPE_MESSAGE_SENT = 1;
    public static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


    public Message(){
        this.id = CommonUtil.generateUniqueId();
        this.text = "This is a message.";
        this.senddate = new Date();
    }

    public Message(String text, int type){
        this.id = CommonUtil.generateUniqueId();
        this.senddate = new Date();
        this.text = text;
        this.type = type;
    }

    public Message(int seq){
        this.id = CommonUtil.generateUniqueId();
        this.text = "This is a message #" + seq;
        this.senddate = new Date();
        this.setType(seq%2+1);
        this.senderId = "User #"+(seq%2+1);
    }

    public long getId() {
        return id;
    }

    public Date getSenddate() {
        return senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderId() {
        return senderId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId='" + senderId + '\'' +
                ", text='" + text + '\'' +
                ", senddate=" + senddate +
                ", type=" + type +
                '}';
    }
}
