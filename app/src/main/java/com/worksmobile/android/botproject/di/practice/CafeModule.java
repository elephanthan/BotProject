package com.worksmobile.android.botproject.di.practice;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = CoffeeComponent.class)
public class CafeModule {

    private String name;

    public CafeModule(){

    }

    public CafeModule(String name){
        this.name = name;
    }

    @Singleton
    @Provides
    CafeInfo cafeInfo(){
        if(name == null || name.isEmpty())  return new CafeInfo();
        else    return new CafeInfo(name);
    }
}