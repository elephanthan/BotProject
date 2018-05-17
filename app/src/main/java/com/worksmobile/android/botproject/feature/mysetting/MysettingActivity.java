package com.worksmobile.android.botproject.feature.mysetting;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.beacon.MonitoringService;
import com.worksmobile.android.botproject.beacon.SettingInfo;
import com.worksmobile.android.botproject.model.User;
import com.worksmobile.android.botproject.util.SharedPrefUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.worksmobile.android.botproject.feature.splash.SplashActivity.retrofitClient;

public class MysettingActivity extends AppCompatActivity {

    @BindView(R.id.layout_mysetting)
    View mysettingLayout;
    @BindView(R.id.switch_beacon_monitoring)
    Switch bgmonitoringSwitch;
    @BindView(R.id.imageview_profile)
    ImageView profileImageView;
    @BindView(R.id.edittext_nickname)
    EditText nicknameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysetting);
        ButterKnife.bind(this);

        String employeeNumber = SharedPrefUtil.getStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID);
        bgmonitoringSwitch.setOnCheckedChangeListener((compoundButton, bChecked) -> bgmonitoringSwitchChanged(bChecked));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.barname_mysetting);
        }

        retrofitClient.getUser(employeeNumber, new ApiRepository.RequestUserCallback() {
            @Override
            public void success(User user) {
                if (user != null) {
                    Glide.with(MysettingActivity.this).load(user.getProfile()).placeholder(R.drawable.ic_icon_man).error(R.drawable.ic_icon_man).into(profileImageView);
                    nicknameEditText.setText(user.getName());
                }
            }

            @Override
            public void error(Throwable throwable) {
                Toast.makeText(MysettingActivity.this, R.string.alert_network_connection_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.isBackgroundMonitoringServiceRunning(this)) {
            bgmonitoringSwitch.setChecked(true);
        }
    }

    private void bgmonitoringSwitchChanged(boolean bChecked) {
        if (bChecked) {
            BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();

            if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBTIntent, SettingInfo.REQUEST_ENABLE_BT);
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is not granted.");
                    requestLocationPermission();
                } else {
                    Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is already granted.");
                }
            }

            Intent intent = new Intent(this, MonitoringService.class);
            startService(intent);
        } else {
            stopService(new Intent(this, MonitoringService.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SettingInfo.REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            //If the request to turn on bluetooth is denied, the app will be finished.
            //사용자가 블루투스 요청을 허용하지 않았을 경우, 어플리케이션은 종료됩니다.
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isBackgroundMonitoringServiceRunning(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo runningService : am.getRunningServices(Integer.MAX_VALUE)) {
            if(MonitoringService.class.getName().equals(runningService.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void requestLocationPermission() {
        if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, SettingInfo.REQUEST_LOCATION);
        }

        Snackbar.make(mysettingLayout, R.string.location_permission_rationale, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.command_OK, v -> ActivityCompat.requestPermissions(MysettingActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, SettingInfo.REQUEST_LOCATION))
                .show();
    }
}
