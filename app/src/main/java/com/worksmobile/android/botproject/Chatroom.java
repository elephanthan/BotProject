package com.worksmobile.android.botproject;

import android.widget.ImageView;

import java.util.UUID;

/**
 * Created by user on 2018. 3. 27..
 */

public class Chatroom {

    private UUID mId;
    private String mTitle;
    private int mTumbnail;

    public Chatroom(){
        mId = UUID.randomUUID();
    }

    public Chatroom(String title, int resId){
        mId = UUID.randomUUID();
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
}
