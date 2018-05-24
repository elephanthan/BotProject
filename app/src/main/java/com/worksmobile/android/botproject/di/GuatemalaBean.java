package com.worksmobile.android.botproject.di;

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