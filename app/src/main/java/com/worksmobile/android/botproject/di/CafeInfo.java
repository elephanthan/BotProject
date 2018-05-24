package com.worksmobile.android.botproject.di;

import android.util.Log;

public class CafeInfo {

    private String name;

    public CafeInfo(){}

    public CafeInfo(String name){ this.name = name; }

    public void welcome(){
//        System.out.println("Welcome " + name == null? "":name );
        Log.d("##", "Welcome " + (name == null? "":name ));
    }

}