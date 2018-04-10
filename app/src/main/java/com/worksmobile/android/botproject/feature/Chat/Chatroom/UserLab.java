package com.worksmobile.android.botproject.feature.Chat.Chatroom;

import com.worksmobile.android.botproject.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 4. 9..
 */

public class UserLab {
    private static UserLab userLab;
    private List<User> users;

    public static UserLab get(){
        if(userLab == null) {
            return new UserLab();
        }
        return userLab;
    }

    private UserLab(){
        users = new ArrayList<>();
        for(int i=0;i<5;i++){
            User user = new User(i+"번째 유저");
            users.add(user);
        }
    }

    public List<User> getUsers(){
        return users;
    }

    public User getUser(long id){
        for(User user : users){
            if(user.getUserId().equals(id)){
                return user;
            }
        }
        return new User();
    }
}
