package com.worksmobile.android.botproject.api;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RetrofitClient2 extends ApiRepository {
    public static final String TAG = RetrofitClient.class.getSimpleName();

    private ApiService2 service;
    Gson gson;

    public RetrofitClient2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Uri.Builder().scheme(SCHEME2).encodedAuthority(AUTHORITY2).build().toString())
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
        service = retrofit.create(ApiService2.class);

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
    public void getDummyImages(int size, final RequestStringListCallback callback) {
        service.getDummyImages(size, "picture").enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable error) {
                callback.error(error);
            }
        });
    }

    public interface ApiService2 {
        @GET("api")
        Call<List<String>> getDummyImages(@Query("results") int results, @Query("Inc") String inc);
    }
}