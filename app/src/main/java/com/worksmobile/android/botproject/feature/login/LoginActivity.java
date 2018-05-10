package com.worksmobile.android.botproject.feature.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.api.MqttRepository;
import com.worksmobile.android.botproject.api.RetrofitClient;
import com.worksmobile.android.botproject.feature.chat.chatroomlist.ChatroomListActivity;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Message;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.button_login)
    Button btnLogin;
    @BindView(R.id.edittext_login)
    EditText editTextLogin;

    public static final RetrofitClient retrofitClient = new RetrofitClient();
    public static MqttClient mqttClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_login)
    public void onBtnLoginClicked(){
        SharedPreferences pref = getSharedPreferences("USER_INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String userId = editTextLogin.getText().toString();
        editor.putString("employee_number", userId);
        editor.commit();


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

        mqttClient = MqttRepository.getMqttClient(userId);
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
                Log.i("connectionLost", cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Message message = new Gson().fromJson(mqttMessage.toString(), Message.class);
                Log.i("messageArrived", message.toString() + "fromm topic:" + topic);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                if(token.isComplete()){
                }
            }
        });

        startActivity(new Intent(getApplicationContext(), ChatroomListActivity.class));
    }

}
