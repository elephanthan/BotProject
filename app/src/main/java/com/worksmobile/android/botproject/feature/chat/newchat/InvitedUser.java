package com.worksmobile.android.botproject.feature.chat.newchat;

public class InvitedUser {
    private String userId;

    public InvitedUser(String userId) {
        setUserId(userId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
