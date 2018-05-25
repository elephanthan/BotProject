package com.worksmobile.android.botproject.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class
})
public interface AppComponent {

    void inject(DaggerBotProjectApp daggerBotProjectApp);

}