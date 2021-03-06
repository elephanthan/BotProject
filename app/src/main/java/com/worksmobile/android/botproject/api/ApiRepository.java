package com.worksmobile.android.botproject.api;

import com.worksmobile.android.botproject.feature.chat.newchat.NewchatDataModel;
import com.worksmobile.android.botproject.feature.chat.newchat.TalkerDataModel;
import com.worksmobile.android.botproject.model.Chatbox;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.model.User;

import java.util.List;

/**
 * Created by user on 2018. 4. 16..
 */

public interface ApiRepository {
    public static String SCHEME = "http";
    public static String AUTHORITY = "10.106.150.71:8080";
//    public static String AUTHORITY = "10.66.76.25:8080";

    void loginUser(String userId, RequestStringCallback callback);

    void getChatroomList(String userId, RequestChatroomListCallback callback);

    void getChatbox(long chatroomId, String employeeNumber, RequestChatboxCallback callback);

    void getMessagesByScroll(long chatroomId, long id, int scrollDirection, RequestMessagesCallback requestMessagesCallback);

    void sendBeaconEvent(String userId, String uuid, int major, int minor, int signal, double distance, RequestVoidCallback requestVoidCallback);

    void startNewchat(NewchatDataModel newchatDataModel, RequestChatroomCallback requestChatroomCallback);

    void getUser(String userId, RequestUserCallback callback);

    interface RequestUserCallback {
        void success(User user);
        void error(Throwable throwable);
    }

    interface RequestChatroomListCallback {
        void success(List<Chatroom> chatrooms);
        void error(Throwable throwable);

    }

    interface RequestStringCallback {
        void success(String string);
        void error(Throwable throwable, String message);

    }

    interface RequestChatroomCallback {
        void success(Chatroom chatroom);
        void error(Throwable throwable);

    }
    interface RequestChatboxCallback {
        void success(Chatbox Chatbox);
        void error(Throwable throwable);
    }

    interface RequestMessagesCallback {
        void success(List<Message> messages);
        void error(Throwable throwable);
    }

    interface RequestVoidCallback {
        void success();
        void error(Throwable throwable);
    }

    interface RequestTalkerCallback {
        void success(TalkerDataModel talkerDataModel);
        void error(Throwable throwable);
    }
}
