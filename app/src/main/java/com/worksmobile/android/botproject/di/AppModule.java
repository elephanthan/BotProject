package com.worksmobile.android.botproject.di;

import android.app.Activity;

import com.worksmobile.android.botproject.feature.chat.chatroomlist.ChatroomListActivity;
import com.worksmobile.android.botproject.feature.chat.chatroomlist.ChatroomListComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = ChatroomListComponent.class)
abstract class AppModule {

//    @Singleton
//    @Binds
//    abstract DataSource bindDataSource(DataSourceImpl dataSource);

    @Binds
    @IntoMap
    @ActivityKey(ChatroomListActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindChatroomListActivity(ChatroomListComponent.Builder builder);
}
