package com.worksmobile.android.botproject.feature.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.feature.chat.chatroomlist.ChatroomListActivity;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.util.SharedPrefUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.worksmobile.android.botproject.feature.splash.SplashActivity.retrofitClient;


public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.button_login)
    Button btnLogin;
    @BindView(R.id.edittext_login)
    EditText editTextLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_login)
    public void onBtnLoginClicked(){
        String userId = editTextLogin.getText().toString();
        SharedPrefUtil.setStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID, userId);

        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        retrofitClient.loginUser(map, new ApiRepository.RequestChatroomListCallback() {
            @Override
            public void success(List<Chatroom> chatrooms) {
//                Log.d("rf loginUser Success", chatrooms.toString());
            }

            @Override
            public void error(Throwable throwable) {
                Log.d("retrofit error", "Retrofit Error ::: loginUser");
            }
        });

        startActivity(new Intent(getApplicationContext(), ChatroomListActivity.class));
    }

}
