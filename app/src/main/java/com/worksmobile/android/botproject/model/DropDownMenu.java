package com.worksmobile.android.botproject.model;

/**
 * Created by user on 2018. 5. 7..
 */

public class DropDownMenu {
    int name;
    int iconImg;

    public DropDownMenu(int name, int iconImg) {
        this.name = name;
        this.iconImg = iconImg;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getIconImg() {
        return iconImg;
    }

    public void setIconImg(int iconImg) {
        this.iconImg = iconImg;
    }
}
