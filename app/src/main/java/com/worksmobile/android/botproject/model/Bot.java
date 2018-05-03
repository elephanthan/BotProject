package com.worksmobile.android.botproject.model;

/**
 * Created by user on 2018. 4. 26..
 */

public class Bot extends Talker {
    private int botId;
    private String botName;
    private int botImage;

    public Bot(){
        this.botId = 0;
        this.botName = "chatbot";
    }

    public Bot(int id, String name){
        this.botId = id;
        this.botName = name;
    }

    public int getBotId() {
        return botId;
    }

    public String getName() {
        return botName;
    }

    public int getType() {
        return TALKER_TYPE_BOT;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public int getBotImage() {
        return botImage;
    }

    public void setBotImage(int botImage) {
        this.botImage = botImage;
    }
}
