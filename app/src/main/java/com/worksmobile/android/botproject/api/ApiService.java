package com.worksmobile.android.botproject.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("comments")
    Call<ResponseBody> getComment(@Query("postId") int postId);

}