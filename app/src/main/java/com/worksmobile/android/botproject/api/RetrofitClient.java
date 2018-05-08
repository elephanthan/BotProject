package com.worksmobile.android.botproject.api;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.model.User;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by user on 2018. 4. 9..
 */

public class RetrofitClient implements  ApiRepository {
    public static final String TAG = RetrofitClient.class.getSimpleName();

    private ApiService service;
    Gson gson;

    public RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Uri.Builder().scheme(SCHEME).encodedAuthority(AUTHORITY).build().toString())
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
        service = retrofit.create(ApiService.class);

        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

    @Override
    public void loginUser(Map<String, String> map, final RequestChatroomListCallback callback){
        service.loginUser(gson.toJson(map)).enqueue(new Callback<List<Chatroom>>() {
            @Override
            public void onResponse(Call<List<Chatroom>> call, Response<List<Chatroom>> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<List<Chatroom>> call, Throwable error) {
                callback.error(error);
            }
        });
    }

    @Override
    public void getChatroomList(String userId, final RequestChatroomListCallback callback) {
        service.getChatroomList(userId).enqueue(new Callback<List<Chatroom>>() {
            @Override
            public void onResponse(Call<List<Chatroom>> call, Response<List<Chatroom>> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<List<Chatroom>> call, Throwable error) {
                callback.error(error);
            }
        });
    }

    public interface ApiService {
        @GET("comments")
        Call<List<Object>> getComment(@Query("id") int id);

        @POST("login")
        Call<List<Chatroom>> loginUser(@Body String json);

        @GET("chatrooms")
        Call<List<Chatroom>> getChatroomList(@Query("userId") String userId);

        @GET("chatrooms")
        Call<List<Chatroom>> getChatroomsByUserId(@Query("userId") String userId);

        @GET("chatrooms/{chatroomId}")
        Call<Chatroom> getChatroom(@Path("chatroomId") String chatroomId);

        @POST("chatrooms")
        Call<Chatroom> createChatroom(@Body Chatroom chatroom);

        @PATCH("chatrooms/{chatroomId}")
        Call<Chatroom> updateChatroom(@Body Map<String, String> map);

        @POST("user_chatrooms/{chatroomId}")
        Call<List<User>> inviteUsers(@Path("chatroomId") String chatroomId, @Body List<User> users);

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
}
