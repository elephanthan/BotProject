package com.worksmobile.android.botproject.api;

import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018. 4. 16..
 */

public abstract class ApiRepository {
    public static String SCHEME = "http";
    public static String SCHEME2 = "https";
    public static String AUTHORITY = "10.106.150.71:8080";
    public static String AUTHORITY2 = "randomuser.me";

    void loginUser(Map<String, String> map, RequestChatroomListCallback callback);
    void getChatroomList(String userId, RequestChatroomListCallback callback);
    public static final String IMAGE_URL = "http://10.66.76.40:8081/image/";
    public static final String IMAGE_PROFILE_EXT = ".jpeg";

    }

    void getChatroomList(String userId, RequestChatroomListCallback callback) {

    }

    public void getDummyImages(int size, final RequestStringListCallback callback) {

    }

    public interface RequestUserCallback {
        void success(User user);
        void error(Throwable throwable);
    }

    public interface RequestChatroomListCallback {
        void success(List<Chatroom> chatrooms);
        void error(Throwable throwable);

    }

    public interface RequestChatroomCallback {
        void success(Chatroom chatroom);
        void error(Throwable throwable);

    }

    public interface RequestStringListCallback {
        void success(List<String> strList);
        void error(Throwable throwable);

    }
}
