package com.worksmobile.android.botproject.api;

import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.User;

import java.util.List;

/**
 * Created by user on 2018. 4. 16..
 */

public interface ApiRepository {
    public static String SCHEME = "http";
    public static String AUTHORITY = "10.106.150.71:8080";

    void loginUser(ApiShipper shipper, RequestChatroomListCallback callback);
    void getChatroomList(String userId, RequestChatroomListCallback callback);

    interface RequestUserCallback {
        void success(User user);
        void error(Throwable throwable);
    }

    interface RequestChatroomListCallback {
        void success(List<Chatroom> chatrooms);
        void error(Throwable throwable);

    }

    interface RequestChatroomCallback {
        void success(Chatroom chatroom);
        void error(Throwable throwable);

    }
}
