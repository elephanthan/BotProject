package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.support.annotation.NonNull;
import android.util.Log;

import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.model.Chatroom;

import java.util.ArrayList;
import java.util.List;

import static com.worksmobile.android.botproject.feature.login.LoginActivity.retrofitClient;

public class ChatroomListPresenter implements ChatroomListContract.Presenter{

    private final ChatroomListContract.View chatroomListView;
    private final ChatroomListContract.AdapterView chatroomAdapterView;

    List<Chatroom> chatroomsToShow = new ArrayList<Chatroom>();

    public ChatroomListPresenter(@NonNull ChatroomListContract.View chatroomListView, @NonNull ChatroomListContract.AdapterView chatroomAdapterView) {
        this.chatroomListView = chatroomListView;
        this.chatroomListView.setChatroomListPresenter(this);
        this.chatroomAdapterView = chatroomAdapterView;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadChatrooms(String userId) {
        //List<Chatroom> chatroomsToShow = new ArrayList<Chatroom>();
        retrofitClient.getChatroomList(userId, new ApiRepository.RequestChatroomListCallback() {
            @Override
            public void success(List<Chatroom> chatroomList) {
                Log.d("retrofit Success", chatroomList.toString());
                chatroomsToShow = chatroomList;
                chatroomAdapterView.refresh(chatroomsToShow);
                chatroomListView.showChatrooms();
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("retrofit error", "Retrofit Error ::: loginUser");
            }
        });
    }
}
