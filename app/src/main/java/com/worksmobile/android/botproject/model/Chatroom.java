package com.worksmobile.android.botproject.model;

import com.google.gson.annotations.SerializedName;
import com.worksmobile.android.botproject.util.CommonUtil;

/**
 * Created by user on 2018. 3. 27..
 */

public class Chatroom {

    public static final int CHATROOM_TYPE_USER = 0;
    public static final int CHATROOM_TYPE_BOT = 1;

    @SerializedName("chatroomId")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("userCnt")
    private int number;
    @SerializedName("chatroomType")
    private int chatroomType;
    @SerializedName("lastMessageContent")
    private String lastMessageContent;
    @SerializedName("lastMessageTime")
    private String lastMessageTime;
    @SerializedName("profile")
    private String profile;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Chatroom(){
        this.id = CommonUtil.generateUniqueId();
        this.title = "채팅방";
    }

    public Chatroom(long id) {
        this.id = id;
        this.title = "채팅방";
    }

    public Chatroom(String title, int resId){
        this.id = CommonUtil.generateUniqueId();
        this.title = title;
//        this.thumbnail = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }


    public String getLastMessageContent() {
        return lastMessageContent;
    }

    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public int getChatroomType() {
        return chatroomType;
    }

    public void setChatroomType(int chatroomType) {
        this.chatroomType = chatroomType;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
