package com.worksmobile.android.botproject.model;

/**
 * Created by user on 2018. 4. 26..
 */

public abstract class Talker {
    public static final int TALKER_TYPE_USER = 0;
    public static final int TALKER_TYPE_BOT = 1;

    private boolean isChecked = false;

    abstract public int getType();

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    abstract public String getName();

}
