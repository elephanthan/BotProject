package com.worksmobile.android.botproject.feature.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.feature.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        CountDownTimer timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();


            }
        };
        timer.start();
    }
}