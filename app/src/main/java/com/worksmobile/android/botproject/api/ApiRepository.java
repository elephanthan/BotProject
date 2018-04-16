package com.worksmobile.android.botproject.api;

import android.net.Uri;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018. 4. 16..
 */

public interface ApiRepository {
    public static String SCHEME = "http";
    public static String AUTHORITY = "jsonplaceholder.typicode.com";
//    public static String PATH = "/comments";

    Uri uri = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
//            .path(PATH)
            //.appendQueryParameter("id", "1")
            .build();

    void getComment(Map<String, String> map, RequestCallback callback);

    void getPosts(Map<String, String> map, RequestCallback callback);

    interface RequestCallback {
        // 생성시의 Callback
        void success(List<Object> objects);

        // 실패시의 Callback
        void error(Throwable throwable);
    }

}
