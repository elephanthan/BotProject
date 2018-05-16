package com.worksmobile.android.botproject.model;

public interface ChatroomDataModel {
    void add(Chatroom chatroom);
    Chatroom remove(int position);
    Chatroom getChatroom(int position);
    int getSize();
}
