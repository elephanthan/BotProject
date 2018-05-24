package com.worksmobile.android.botproject.feature.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.di.CafeComponent;
import com.worksmobile.android.botproject.di.CafeInfo;
import com.worksmobile.android.botproject.di.CafeModule;
import com.worksmobile.android.botproject.di.CoffeeBean;
import com.worksmobile.android.botproject.di.CoffeeComponent;
import com.worksmobile.android.botproject.di.CoffeeMaker;
import com.worksmobile.android.botproject.di.DaggerCafeComponent;
import com.worksmobile.android.botproject.feature.chat.chatroomlist.ChatroomListActivity;
import com.worksmobile.android.botproject.util.SharedPrefUtil;

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


        // practice dagger
        CafeComponent cafeComponent = DaggerCafeComponent.create();
        CafeInfo cafeInfo1 = cafeComponent.cafeInfo(); // 동일한 singleton scope 이기 때문에 같은 객체가 리턴
        CafeInfo cafeInfo2 = cafeComponent.cafeInfo();
        System.out.println("Singletone CafeInfo is equal : " + cafeInfo1.equals(cafeInfo2));

//CoffeeScope
        CoffeeComponent coffeeComponent1 = cafeComponent.coffeeComponent().build();
        CoffeeComponent coffeeComponent2 = cafeComponent.coffeeComponent().build();
        CoffeeMaker coffeeMaker1 = coffeeComponent1.coffeeMaker();
        CoffeeMaker coffeeMaker2 = coffeeComponent1.coffeeMaker();
        System.out.println("Makere / same component coffeeMaker is equal : " + coffeeMaker1.equals(coffeeMaker2));
        CoffeeMaker coffeeMaker3 = coffeeComponent2.coffeeMaker(); //MakerScopeMethod
        System.out.println("Makere / different component coffeeMaker is equal : " + coffeeMaker1.equals(coffeeMaker3));

//Non-scope
        CoffeeBean coffeeBean1 = coffeeComponent1.coffeeBean();
        CoffeeBean coffeeBean2 = coffeeComponent1.coffeeBean();
        System.out.println("Non-scopedeebean is equal : " + coffeeBean1.equals(coffeeBean2));

        CafeComponent cafeComponent2 = DaggerCafeComponent.builder()
                .cafeModule(new CafeModule("example cafe"))
                .build();
        cafeComponent2.cafeInfo().welcome();

        CoffeeComponent coffeeComponent3 = DaggerCafeComponent.create().coffeeComponent().build();
        coffeeComponent3.coffeeBeanMap().get("guatemala").name();
    }

    @OnClick(R.id.button_login)
    public void onBtnLoginClicked(){
        String userId = editTextLogin.getText().toString();
        SharedPrefUtil.setStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID, userId);

        retrofitClient.loginUser(userId, new ApiRepository.RequestStringCallback() {
            @Override
            public void success(String message) {
                Log.i("Login Success", message);
                startActivity(new Intent(getApplicationContext(), ChatroomListActivity.class));
//                startActivity(new Intent(getApplicationContext(), RecoRangingActivity.class));
            }

            @Override
            public void error(Throwable throwable, String message) {
                Log.d("Login error", throwable.toString());
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
