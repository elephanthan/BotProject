package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import com.worksmobile.android.botproject.di.ActivityScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = ChatroomListModule.class)
public interface ChatroomListComponent extends AndroidInjector<ChatroomListActivity>{
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ChatroomListActivity>{}
}
