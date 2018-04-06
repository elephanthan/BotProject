package com.worksmobile.android.botproject.model;

import com.worksmobile.android.botproject.util.CommonUtil;

import java.util.List;

/**
 * Created by user on 2018. 3. 27..
 */

public class Chatroom {

    private long id;
    private String title;
    private int thumbnail;
    private Message latestMessage;
    private List<User> participants;
    private int number;

    public Message getLatestMsg() {
        return latestMessage;
    }

    public void setLatestMsg(Message latestMessage) {
        this.latestMessage = latestMessage;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Chatroom(){
        id = CommonUtil.generateUniqueId();
    }

    public Chatroom(String title, int resId){
        id = CommonUtil.generateUniqueId();
        this.title = title;
        this.thumbnail = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTumbnail() {
        return thumbnail;
    }

    public void setTumbnail(int resId) {
        this.thumbnail = resId;
    }

    public long getId() {
        return id;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}