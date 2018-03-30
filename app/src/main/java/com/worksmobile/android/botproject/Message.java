package com.worksmobile.android.botproject;

import java.util.Date;

/**
 * Created by user on 2018. 3. 29..
 */

public class Message {
    private long id;
    private long userId;
    private String text;
    private Date senddate;

    public Message(){
        this.id = CommonUtil.generateUniqueId();
        this.text = "This is a message.";
        this.senddate = new Date();
    }

    public Message(int seq){
        this.id = CommonUtil.generateUniqueId();
        this.text = "This is a message #" + seq;
        this.senddate = new Date();
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

    public long getUserId() {
        return userId;
    }
}
