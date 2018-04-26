package com.worksmobile.android.botproject.model;

/**
 * Created by user on 2018. 4. 26..
 */

public class Talker {
    private int type;
    private boolean isChecked = false;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
