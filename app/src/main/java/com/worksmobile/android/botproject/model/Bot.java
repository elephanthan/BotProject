package com.worksmobile.android.botproject.model;

/**
 * Created by user on 2018. 4. 26..
 */

public class Bot implements Invitable{
    private int botId;
    private String botName;
    private int botImage;
    private int type = 2;
    public Bot(){
        this.botId = 0;
        this.botName = "android";
    }

    public Bot(int id, String name){
        this.botId = id;
        this.botName = name;
    }

    public int getBotId() {
        return botId;
    }

    public void setBotId(int botId) {
        this.botId = botId;
    }

    @Override
    public String getName() {
        return botName;
    }

    @Override
    public int getType() {
        return type;
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
