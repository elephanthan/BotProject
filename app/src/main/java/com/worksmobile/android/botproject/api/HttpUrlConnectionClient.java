package com.worksmobile.android.botproject.api;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018. 4. 16..
 */

public class HttpUrlConnectionClient implements ApiRepository {
    public static final String TAG = HttpUrlConnectionClient.class.getSimpleName();

    @Override
    public void getComment(final Map<String, String> map, final RequestCallback callback) {
        new MyTask(map, callback).execute();
    }

    @Override
    public void getPosts(Map<String, String> map, RequestCallback callback) {
        new MyTask(map, callback).execute();
    }

    private static class MyTask extends AsyncTask<Void, Void, List<Object>>{

        Map<String, String> map;
        RequestCallback callback;

        MyTask(Map<String, String> map, RequestCallback callback){
            this.map = map;
            this.callback = callback;
        }

        // 처리 전에 호출되는 메소드
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 처리를 하는 메소드
        @Override
        protected List<Object> doInBackground(Void... params) {
            final HttpURLConnection urlConnection;
            try {
                //if(map.get("key")!=null){
                Uri.Builder builder = uri.buildUpon();
                builder.path(map.get("PATH"));
                builder.appendQueryParameter(map.get("key"), map.get("value"));

                Uri uri = builder.build();
                URL url = new URL(uri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
            String buffer = new String();
            String str;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                str = reader.readLine();
                buffer += str;
                while(true) {
                    str = reader.readLine();
                    if(str==null)
                        break;

                    buffer += str;
                }
            } catch (IOException e) {
                return null;
            } finally {
                urlConnection.disconnect();
            }
            if (TextUtils.isEmpty(buffer)) {
                return null;
            }

            //if(buffer.charAt(0)=='[')
                return new Gson().fromJson(buffer, new TypeToken<ArrayList<Object>>(){}.getType());
        }

        // 처리가 모두 끝나면 불리는 메소드
        @Override
        protected void onPostExecute(List<Object> response) {
            super.onPostExecute(response);
            // 통신 실패로 처리
            if (response == null) {
                callback.error(new IOException("HttpURLConnection request error"));
            } else {
                Log.d(TAG, "result: " + response.toString());
                // 통신 결과를 표시
                callback.success(response);
            }
        }
    }
}