package com.worksmobile.android.botproject.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018. 3. 30..
 */

public class User extends Talker {
    private String userId;
    @SerializedName("nickname")
    private String userName;
    private int userImg;
    private int isConnect;

    public User() {

    }

    public User(String userName){
        this.userId = userName;
        this.userName = userName;
    }

    public int getType() {
        return TALKER_TYPE_USER;
    }

    public String getId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserImg() {
        return userImg;
    }

    public void setUserImg(int userImg) {
        this.userImg = userImg;
    }
}
