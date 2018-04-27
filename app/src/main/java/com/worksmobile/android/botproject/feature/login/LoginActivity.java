package com.worksmobile.android.botproject.feature.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.api.RetrofitClient;
import com.worksmobile.android.botproject.feature.chat.chatroomlist.ChatroomListActivity;
import com.worksmobile.android.botproject.model.Chatroom;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.button_login)
    Button btnLogin;
    @BindView(R.id.edittext_login)
    EditText editTextLogin;

    private RetrofitClient retrofitClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        retrofitClient = new RetrofitClient();
    }

    @OnClick(R.id.button_login)
    public void onBtnLoginClicked(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String userId = editTextLogin.getText().toString();
        editor.putString("employee_number", userId);
        editor.commit();

        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.addProperty("userId", userId);

        retrofitClient.loginUser(json, new ApiRepository.RequestChatroomListCallback() {
            @Override
            public void success(List<Chatroom> chatrooms) {
//                Log.d("retrofit Success", chatrooms.toString());
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("retrofit error", "hoyahoya");
            }
        });

        startActivity(new Intent(getApplicationContext(), ChatroomListActivity.class));
    }

}
