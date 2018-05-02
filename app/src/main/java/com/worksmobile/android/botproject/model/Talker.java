package com.worksmobile.android.botproject.model;

/**
 * Created by user on 2018. 4. 26..
 */

public abstract class Talker {
    public static final int TALKER_TYPE_USER = 1;
    public static final int TALKER_TYPE_BOT = 2;

    private int type;
    private boolean isChecked = false;
    private String name;

    abstract public int getType();

    public void setType(int type) {
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    abstract public String getName();

    public void setName(String name) {
        this.name = name;
    }
}
