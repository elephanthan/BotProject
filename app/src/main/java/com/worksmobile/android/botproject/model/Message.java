package com.worksmobile.android.botproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.worksmobile.android.botproject.util.CommonUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2018. 3. 29..
 */

public class Message {
    public static final int VIEW_TYPE_MESSAGE_DAY = 2;
    public static final int VIEW_TYPE_MESSAGE_SENT = 0;
    public static final int VIEW_TYPE_MESSAGE_RECEIVED = 1;

    @Expose
    private long chatroomId;

    @SerializedName("messageId")
    private long id;

    @Expose
    @SerializedName("srcUserId")
    private String senderId;

    @Expose
    @SerializedName("content")
    private String content;

    @SerializedName("sendTime")
    private Date senddate;

    @Expose
    @SerializedName("messageType")
    private int messageType;

    private int type;

    public Message(){
        this.id = CommonUtil.generateUniqueId();
        this.content = "This is a message.";
        this.senddate = new Date();
    }

    public Message(long chatroomId, String text, int type, String senderId){
        this.chatroomId = chatroomId;
        this.id = CommonUtil.generateUniqueId();
        this.senddate = new Date();
        this.content = text;
        this.type = type;
        this.senderId = senderId;
    }

    public Message(int seq){
        this.id = CommonUtil.generateUniqueId();
        this.content = "This is a message #" + seq;
        this.senddate = new Date();
        this.setType(seq%2);
        this.senderId = "User #"+(seq%2+1);
    }

    public Message(long chatroomId, Date date, int type) {
        this.id = CommonUtil.generateUniqueId();
        this.chatroomId = chatroomId;
        //TODO 요일 설정하기 (ENUM 사용해서)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        this.content = sdf.format(date);
        this.type = type;
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
        return content;
    }

    public void setText(String text) {
        this.content = text;
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

    public long getChatroomId() {
        return chatroomId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "chatroomId=" + chatroomId +
                ", id=" + id +
                ", senderId='" + senderId + '\'' +
                ", content='" + content + '\'' +
                ", senddate=" + senddate +
                ", messageType=" + messageType +
                ", type=" + type +
                '}';
    }
}
