package com.worksmobile.android.botproject.di.practice;

import com.worksmobile.android.botproject.di.practice.CoffeeBean;

import javax.inject.Inject;

public class GuatemalaBean extends CoffeeBean {

    @Inject
    public GuatemalaBean(){
        super();
    }

    public void name(){
        System.out.println("GuatemalaBean");
    }
}