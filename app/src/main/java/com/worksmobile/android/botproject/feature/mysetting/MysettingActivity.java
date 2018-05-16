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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.beacon.RecoBackgroundMonitoringService;
import com.worksmobile.android.botproject.beacon.SettingInfo;
import com.worksmobile.android.botproject.util.SharedPrefUtil;

import static com.worksmobile.android.botproject.api.ApiRepository.IMAGE_PROFILE_EXT;
import static com.worksmobile.android.botproject.api.ApiRepository.IMAGE_URL;

public class MysettingActivity extends AppCompatActivity {

    private View mysettingLayout;
    private Switch bgmonitoringSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysetting);

        mysettingLayout = findViewById(R.id.layout_mysetting);

        String employeeNumber = SharedPrefUtil.getStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID);

        ImageView imageView = (ImageView) findViewById(R.id.imageview_profile);
        String imageUrl = IMAGE_URL + employeeNumber + IMAGE_PROFILE_EXT;
        Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_icon_man).error(R.drawable.ic_icon_man).into(imageView);

        bgmonitoringSwitch = (Switch) findViewById(R.id.switch_beacon_monitoring);
        bgmonitoringSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                bgmonitoringSwitchChanged(bChecked);
            }
        });
        getSupportActionBar().setTitle(R.string.barname_mysetting);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.triggers_ok, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_ok:
                Toast.makeText(this, "유저 설정 변경", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

            Intent intent = new Intent(this, RecoBackgroundMonitoringService.class);
            startService(intent);
        } else {
            stopService(new Intent(this, RecoBackgroundMonitoringService.class));
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
            if(RecoBackgroundMonitoringService.class.getName().equals(runningService.service.getClassName())) {
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
                .setAction(R.string.command_OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(MysettingActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, SettingInfo.REQUEST_LOCATION);
                    }
                })
                .show();
    }
}
