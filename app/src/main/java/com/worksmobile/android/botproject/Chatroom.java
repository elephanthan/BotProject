package com.worksmobile.android.botproject;

/**
 * Created by user on 2018. 3. 27..
 */

public class Chatroom {

    private long id;
    private String title;
    private int mTumbnail;

    public Chatroom(){
        id = CommonUtil.generateUniqueId();
    }

    public Chatroom(String title, int resId){
        id = CommonUtil.generateUniqueId();
        this.title = title;
        this.mTumbnail = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTumbnail() {
        return mTumbnail;
    }

    public void setTumbnail(int resId) {
        this.mTumbnail = resId;
    }

    public long getId() {
        return id;
    }
}
