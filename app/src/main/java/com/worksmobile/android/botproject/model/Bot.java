package com.worksmobile.android.botproject.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018. 4. 26..
 */

public class Bot extends Talker {
    @SerializedName("botId")
    private String botId;
    @SerializedName("name")
    private String botName;
    private int botImage;

    public Bot(){

    }

    public Bot(String id, String name){
        this.botId = id;
        this.botName = name;
    }

    public String getId() {
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
