package com.worksmobile.android.botproject.api;


import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.model.User;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    @GET("comments")
    Call<ResponseBody> getComment(@Query("postId") int postId);

    @POST("login")
    Call<User> loginUser(@Body User user);

    @GET("chatrooms")
    Call<List<Chatroom>> getChatroomsByUserId(@Query("userId") String userId);

    @GET("chatrooms/{chatroomId}")
    Call<Chatroom> getChatroom(@Path("chatroomId") String chatroomId);

    @POST("chatrooms")
    Call<Chatroom> createChatroom(@Body Chatroom chatroom);

    @PATCH("chatrooms/{chatroomId}")
    Call<Chatroom> updateChatroom(@Body Map<String, String> map);

    @POST("user_chatrooms/{chatroomId}")
    Call<List<User>> inviteUsers(@Path("chatroomId")String chatroomId, @Body List<User> users);

    @DELETE("user_chatrooms/{chatroomId}")
    Call<User> leaveChatroom(@Path("chatroomId") String chatroomId, @Body User user);

    @GET("users/{userId}")
    Call<User> getUser(@Path("userId") String userId);

    @GET("users")
    Call<User> getUsers(@QueryMap User user);


    @PUT("users/{userId}")
    Call<User> updateUser(@Path("userId") String userId, @Body User user);

    @GET("messages")
    Call<List<Message>> getMessages(@QueryMap Message message);

    @POST("bots")
    Call<List<Map>> sendBotEvent(@Body List<Map> maps);

}