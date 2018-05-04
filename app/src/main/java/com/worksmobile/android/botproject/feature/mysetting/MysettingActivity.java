package com.worksmobile.android.botproject.feature.mysetting;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.beacon.RecoBackgroundMonitoringService;

public class MysettingActivity extends AppCompatActivity {

    Switch bgmonitoringSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysetting);

        bgmonitoringSwitch = (Switch) findViewById(R.id.switch_item2);
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
                Toast.makeText(this, "유저 정보 수정", Toast.LENGTH_LONG).show();
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
            Log.i("MainActivity", "onMonitoringToggleButtonClicked off to on");
            Intent intent = new Intent(this, RecoBackgroundMonitoringService.class);
            startService(intent);
        } else {
            Log.i("MainActivity", "onMonitoringToggleButtonClicked on to off");
            stopService(new Intent(this, RecoBackgroundMonitoringService.class));
        }
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
}
