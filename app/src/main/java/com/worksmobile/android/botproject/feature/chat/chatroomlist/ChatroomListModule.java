package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import com.worksmobile.android.botproject.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatroomListModule {

    @ActivityScope
    @Provides
    ChatroomListContract.View bindView(ChatroomListActivity activity) {
        return new ChatroomListActivity();
    }

    @ActivityScope
    @Provides
    ChatroomListContract.AdapterView bindAdapterView(ChatroomListActivity chatroomListActivity) {
        return new ChatroomListAdapter(chatroomListActivity);
    }

    @ActivityScope
    @Provides
    ChatroomListDataModel bindChatroomListDataModel(ChatroomListActivity chatroomListActivity) {
        return new ChatroomListAdapter(chatroomListActivity);
    }

//    @ActivityScope
//    @Binds
//    public abstract ChatroomListAdapter bindAdapter(ChatroomListAdapter adapter);

    @ActivityScope
    @Provides
    public ChatroomListContract.Presenter bindPresenter(ChatroomListContract.View view, ChatroomListContract.AdapterView adapterView, ChatroomListDataModel dataModel) {
        return new ChatroomListPresenter(view, adapterView, dataModel);

    }

}
