package com.worksmobile.android.botproject.feature.chat.newchat;

import com.google.gson.annotations.SerializedName;
import com.worksmobile.android.botproject.model.Bot;
import com.worksmobile.android.botproject.model.Talker;
import com.worksmobile.android.botproject.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 5. 2..
 */

public class TalkerDataModel {

    @SerializedName("allUsers")
    private List<User> users;
    @SerializedName("allBots")
    private List<Bot> bots;

    public List<? extends Talker> getTalkers(int talkerType) {
        if (talkerType == Talker.TALKER_TYPE_USER) {
           List<? extends Talker> talkers = getUsers();
           return talkers;
        } else {
            List<? extends Talker> bots = getBots();
            return bots;
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = new ArrayList<>(users);
    }

    public List<Bot> getBots() {
        return bots;
    }

    public void setBots(ArrayList<Bot> bots) {
        this.bots = new ArrayList<>(bots);
    }
}
