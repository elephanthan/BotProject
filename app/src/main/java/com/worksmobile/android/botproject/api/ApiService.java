package com.worksmobile.android.botproject.api;


import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("comments")
    Call<ResponseBody> getComment(@Query("postId") int postId);

    @POST("login")
    Call<User> loginUser(@Body User user);

    @GET("chatrooms")
    Call<List<Chatroom>> getChatroomListByUserId(@Path("userId") String userId);

    @GET("users")
    Call<User> getUser(@Query("userId") String userId);

}