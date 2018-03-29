package com.worksmobile.android.botproject;

/**
 * Created by user on 2018. 3. 27..
 */

public class Chatroom {

    private long id;
    private String title;
    private int thumbnail;
    private Message latestMsg;
    private int number;

    public Message getLatestMsg() {
        return latestMsg;
    }

    public void setLatestMsg(Message latestMsg) {
        this.latestMsg = latestMsg;
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
}
