package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.support.annotation.NonNull;

public class ChatroomListPresenter implements ChatroomListContract.Presenter{

    private final ChatroomListContract.View chatroomListView;

    public ChatroomListPresenter(@NonNull ChatroomListContract.View chatroomListView) {
        this.chatroomListView = chatroomListView;
        this.chatroomListView.setChatroomListPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadChatrooms() {

    }
}
