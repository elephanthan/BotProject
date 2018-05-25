package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.support.annotation.NonNull;
import android.util.Log;

import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.model.Chatroom;

import java.util.List;

import javax.inject.Inject;

import static com.worksmobile.android.botproject.feature.splash.SplashActivity.retrofitClient;

public class ChatroomListPresenter implements ChatroomListContract.Presenter{

    private final ChatroomListContract.View chatroomListView;
    private final ChatroomListContract.AdapterView chatroomAdapterView;
    private final ChatroomListDataModel chatroomDataModel;

    @Inject
    public ChatroomListPresenter(@NonNull ChatroomListContract.View view, ChatroomListContract.AdapterView adapterView, @NonNull ChatroomListDataModel dataModel) {
        this.chatroomListView = view;
        this.chatroomListView.setChatroomListPresenter(this);
        this.chatroomAdapterView = adapterView;
        this.chatroomDataModel = dataModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadChatrooms(String userId) {
        retrofitClient.getChatroomList(userId, new ApiRepository.RequestChatroomListCallback() {
            @Override
            public void success(List<Chatroom> chatroomList) {
                Log.d("retrofit success", "getChatroomList");
                processChatrooms(chatroomList);
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("retrofit error", "Retrofit Error ::: loginUser");
            }
        });
    }

    private void processChatrooms(List<Chatroom> chatroomList) {
        chatroomAdapterView.refresh(chatroomList);

        if(chatroomList == null || chatroomList.size() == 0){
            chatroomListView.showNoChatrooms();
        } else {
            chatroomListView.showChatrooms();
        }
    }

    @Override
    public void enterChatroom(int position) {
        Chatroom chatroom = chatroomDataModel.getChatroom(position);
        chatroomListView.moveToChatroom(chatroom.getId(), chatroom.getChatroomType());
    }

}
