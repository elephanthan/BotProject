package com.worksmobile.android.botproject.api;

import com.worksmobile.android.botproject.model.Chatbox;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018. 4. 16..
 */

public interface ApiRepository {
    public static String SCHEME = "http";
    public static String AUTHORITY = "10.106.150.71:8080";

    public static final String IMAGE_URL = "http://10.66.76.40:8081/image/";
    public static final String IMAGE_PROFILE_EXT = ".jpeg";

    void loginUser(Map<String, String> map, RequestChatroomListCallback callback);

    void getChatroomList(String userId, RequestChatroomListCallback callback);

    void getChatbox(long chatroomId, String employeeNumber, RequestChatboxCallback callback);

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
    interface RequestChatboxCallback {
        void success(Chatbox Chatbox);
        void error(Throwable throwable);
    }
}
