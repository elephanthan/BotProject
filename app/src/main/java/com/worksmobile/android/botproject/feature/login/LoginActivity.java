package com.worksmobile.android.botproject.feature.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.feature.chat.chatroomlist.ChatroomListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.button_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_login)
    public void onBtnLoginClicked(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("employee_number", btnLogin.getText().toString());
        editor.commit();

        startActivity(new Intent(getApplicationContext(), ChatroomListActivity.class));
    }

}
