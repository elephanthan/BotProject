package com.worksmobile.android.botproject.model;

/**
 * Created by user on 2018. 3. 30..
 */

public class User extends Talker {
    private String userId;
    private String userName;
    private int userImg;
    private int isConnect;

    public User(){
        this.userId = "AA000001";
        this.userName = "user_unknown";
    }

    public User(String userName){
        this.userId = userName;
        this.userName = userName;
    }

    public int getType() {
        return TALKER_TYPE_USER;
    }

    public String getUserId() {
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
