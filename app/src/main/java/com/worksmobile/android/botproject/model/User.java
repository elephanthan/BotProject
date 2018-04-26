package com.worksmobile.android.botproject.model;

/**
 * Created by user on 2018. 3. 30..
 */

public class User implements Invitable{
    private String userId;
    private String userName;
    private int userImg;
    private int type = 1;

    public User(){
        this.userId = "AA000001";
        this.userName = "chulsoo";
    }

    public User(String userName){
        this.userId = userName;
        this.userName = userName;
    }

    @Override
    public int getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
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
