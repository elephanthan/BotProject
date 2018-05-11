package com.worksmobile.android.botproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Chatbox {
    private long chatroomId;
    private String title;
    private int chatroomType;
    private List<User> chatUsers;
    private Bot chatBot;
    @SerializedName("messageFactorVOs")
    private List<Message> msgList;

    public long getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(long chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChatroomType() {
        return chatroomType;
    }

    public void setChatroomType(int chatroomType) {
        this.chatroomType = chatroomType;
    }

    public List<User> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(List<User> chatUsers) {
        this.chatUsers = chatUsers;
    }

    public Bot getChatBot() {
        return chatBot;
    }

    public void setChatBot(Bot chatBot) {
        this.chatBot = chatBot;
    }

    public List<Message> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }

    @Override
    public String toString() {
        return "Chatbox{" +
                "chatroomId=" + chatroomId +
                ", title='" + title + '\'' +
                ", chatroomType=" + chatroomType +
                ", chatUsers=" + chatUsers +
                ", chatBot=" + chatBot +
                ", msgList=" + msgList +
                '}';
    }
}
