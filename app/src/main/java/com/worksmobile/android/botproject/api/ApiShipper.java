package com.worksmobile.android.botproject.api;

/**
 * Created by user on 2018. 4. 30..
 */

public class ApiShipper {
    private String key;
    private String value;

    public ApiShipper(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
