package com.worksmobile.android.botproject;

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

    public Message(){
        this.id = CommonUtil.generateUniqueId();
        this.text = "This is a message.";
        this.senddate = new Date();
    }

    public Message(int seq){
        this.id = CommonUtil.generateUniqueId();
        this.text = "This is a message #" + seq;
        this.senddate = new Date();
        this.setType(seq%2+1);
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
}
