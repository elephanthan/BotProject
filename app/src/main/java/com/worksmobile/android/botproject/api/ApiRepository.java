package com.worksmobile.android.botproject.api;

import android.net.Uri;

import com.google.gson.JsonObject;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.User;

import java.util.List;

/**
 * Created by user on 2018. 4. 16..
 */

public interface ApiRepository {
    public static String SCHEME = "http";
    public static String AUTHORITY = "10.106.150.71:8080";

    Uri uri = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .build();

//    void getComment(Map<String, String> map, RequestCallback callback);
//
//    void getPosts(Map<String, String> map, RequestCallback callback);

    void loginUser(JsonObject json, RequestChatroomListCallback callback);

    interface RequestChatroomListCallback {
        // 생성시의 Callback
        void success(List<Chatroom> user);

        // 실패시의 Callback
        void error(Throwable throwable);
    }

    interface RequestUserCallback {
        // 생성시의 Callback
        void success(User user);

        // 실패시의 Callback
        void error(Throwable throwable);
    }

}
