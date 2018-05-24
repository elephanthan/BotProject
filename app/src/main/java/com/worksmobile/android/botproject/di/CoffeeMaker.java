package com.worksmobile.android.botproject.di;

import javax.inject.Inject;


public class CoffeeMaker {
    private Heater heater;

    @Inject
    public CoffeeMaker(Heater heater){
        this.heater = heater;

    }


    public void brew(CoffeeBean coffeeBean){
        System.out.println("CoffeeBeen("+coffeeBean.toString() + " coffee! [_]P ");
    }

}