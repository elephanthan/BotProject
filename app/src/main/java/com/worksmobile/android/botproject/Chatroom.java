package com.worksmobile.android.botproject;

/**
 * Created by user on 2018. 3. 27..
 */

public class Chatroom {

    private long mId;
    private String mTitle;
    private int mTumbnail;

    public Chatroom(){
        mId = CommonUtil.generateUniqueId();
    }

    public Chatroom(String title, int resId){
        mId = CommonUtil.generateUniqueId();
        this.mTitle = title;
        this.mTumbnail = resId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getTumbnail() {
        return mTumbnail;
    }

    public void setTumbnail(int resId) {
        this.mTumbnail = resId;
    }

    public long getId() {
        return mId;
    }
}
