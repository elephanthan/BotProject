package com.worksmobile.android.botproject.feature.chat.newchat;

public class InvitedBot {
    private String botId;

    public InvitedBot(String botId) {
        setBotId(botId);
    }

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }
}
