package com.worksmobile.android.botproject.api;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

    private final ApiService service;

    public RetrofitClient(){
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Uri.Builder().scheme(SCHEME).encodedAuthority(AUTHORITY).build().toString())
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
        service = retrofit.create(ApiService.class);
    }

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

    @Override
    public void loginUser(ApiShipper shipper, final RequestChatroomListCallback callback){

        JsonObject json = getJson(shipper);

        service.loginUser(json).enqueue(new Callback<List<Chatroom>>() {
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

    private JsonObject getJson(ApiShipper shipper) {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.addProperty(shipper.getKey(), shipper.getValue());
        return json;
    }

    @Override
    public void getChatroomList(JsonObject json, RequestChatroomListCallback callback) {

    }

    public interface ApiService {
        @GET("comments")
        Call<List<Object>> getComment(@Query("id") int id);

        @POST("login")
        Call<List<Chatroom>> loginUser(@Body JsonObject json);

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
