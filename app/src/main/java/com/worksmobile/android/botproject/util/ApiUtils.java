package com.worksmobile.android.botproject.util;

import com.worksmobile.android.botproject.api.ApiService;
import com.worksmobile.android.botproject.api.RetrofitClient;

public class ApiUtils {

    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
    //public static final String BASE_URL = "http://{serverAddress}/api/v1/";

    public static ApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}