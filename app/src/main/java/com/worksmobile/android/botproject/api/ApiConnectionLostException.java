package com.worksmobile.android.botproject.api;

import android.util.Log;

public class ApiConnectionLostException extends Exception{

    public static final String TAG = ApiConnectionLostException.class.getSimpleName();

    public ApiConnectionLostException() {}

    public ApiConnectionLostException(String message)
    {
        super(message);
        Log.e(TAG, message);
    }
}
