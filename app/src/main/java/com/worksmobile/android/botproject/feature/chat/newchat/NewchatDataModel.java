package com.worksmobile.android.botproject.feature.chat.newchat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.worksmobile.android.botproject.model.Talker;

import java.util.ArrayList;
import java.util.List;

public class NewchatDataModel {
    private String inviter;
    private List<InvitedUser> invitedUsers;
    private InvitedBot invitedBot;

    public NewchatDataModel(String employeeNumber, List<Talker> checkedList) {
        this.inviter = employeeNumber;
        setInvitedUserWithTalkers(checkedList);
    }

    public NewchatDataModel(String employeeNumber, Talker checkedTalker) {
        this.inviter = employeeNumber;
        this.invitedBot = new InvitedBot(checkedTalker.getId());
    }

    public String getInviter() {
        return inviter;
    }

    public List<InvitedUser> getInvitedUser() {
        return invitedUsers;
    }

    private void setInvitedUserWithTalkers(List<Talker> checkedList) {
        this.invitedUsers = new ArrayList<>();
        for (Talker talker: checkedList) {
            InvitedUser invitedUser = new InvitedUser(talker.getId());
            this.invitedUsers.add(invitedUser);
        }
    }

    public InvitedBot getInvitedBot() {
        return invitedBot;
    }

    public JsonObject getJsonObject() {
        Gson gson = new Gson();

        JsonObject newchatJson = new JsonObject();

        newchatJson.addProperty("inviter", getInviter());
        newchatJson.add("invitedUsers", gson.toJsonTree(getInvitedUser()));
        newchatJson.add("invitedBot", gson.toJsonTree(getInvitedBot()));

        return newchatJson;
    }
}
