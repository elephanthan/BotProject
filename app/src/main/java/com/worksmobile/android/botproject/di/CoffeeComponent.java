package com.worksmobile.android.botproject.di;

import java.util.Map;

import dagger.Subcomponent;

@CoffeeScope
@Subcomponent(modules = {
        CoffeeModule.class
        ,CoffeeBeanModule.class
})
public interface CoffeeComponent {
    CoffeeMaker coffeeMaker();
    CoffeeBean coffeeBean();
    Map<String,CoffeeBean> coffeeBeanMap();

    @Subcomponent.Builder
    interface Builder{
        Builder coffeeModule(CoffeeModule coffeeModule);
        CoffeeComponent build();
    }
}