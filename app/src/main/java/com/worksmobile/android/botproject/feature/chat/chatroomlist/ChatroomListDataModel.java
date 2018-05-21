package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import com.worksmobile.android.botproject.model.Chatroom;

import java.util.List;

public interface ChatroomListDataModel {
    void add(Chatroom chatroom);
    Chatroom remove(int position);
    Chatroom getChatroom(int position);
    int getSize();
    List<Chatroom> getAllChatrooms();
}
