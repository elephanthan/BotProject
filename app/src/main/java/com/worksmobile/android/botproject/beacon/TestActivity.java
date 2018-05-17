package com.worksmobile.android.botproject.beacon;

import android.app.NotificationManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECOMonitoringListener;
import com.perples.recosdk.RECOServiceConnectListener;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.util.SharedPrefUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import static com.worksmobile.android.botproject.feature.splash.SplashActivity.retrofitClient;

public class TestActivity extends RecoActivity implements RECOMonitoringListener, RECOServiceConnectListener {

    private long mScanPeriod = 1*1000L;
    private long mSleepPeriod = 1*1000L;

    private int notificationID = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mRecoManager.setMonitoringListener(this);
        mRecoManager.setScanPeriod(mScanPeriod);
        mRecoManager.setSleepPeriod(mSleepPeriod);
        mRecoManager.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.stop(mRegions);
        this.unbind();
    }

    @Override
    public void onServiceConnect() {
        Log.i("RecoMonitoringActivity", "onServiceConnect");
        this.start(mRegions);
        //Write the code when RECOBeaconManager is bound to RECOBeaconService
    }

    @Override
    public void didDetermineStateForRegion(RECOBeaconRegionState recoRegionState, RECOBeaconRegion recoRegion) {
        Log.i("RecoMonitoringActivity", "didDetermineStateForRegion()");
        Log.i("RecoMonitoringActivity", "region: " + recoRegion.getUniqueIdentifier() + ", state: " + recoRegionState.toString());

    }

    @Override
    public void didEnterRegion(RECOBeaconRegion recoRegion, Collection<RECOBeacon> beacons) {
        String employeeNumber = SharedPrefUtil.getStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID);

        for (RECOBeacon beacon : beacons) {
            retrofitClient.sendBeaconEvent(employeeNumber, beacon.getProximityUuid(), beacon.getMajor(), beacon.getMinor(), 1, beacon.getAccuracy(), new ApiRepository.RequestVoidCallback() {
                @Override
                public void success() {
                    popupNotification(recoRegion.getUniqueIdentifier() + "입장");
                }

                @Override
                public void error(Throwable throwable) {

                }
            });
        }
    }

    @Override
    public void didExitRegion(RECOBeaconRegion recoRegion) {

    }

    @Override
    public void didStartMonitoringForRegion(RECOBeaconRegion recoRegion) {

    }

    @Override
    protected void start(ArrayList<RECOBeaconRegion> regions) {

    }

    @Override
    protected void stop(ArrayList<RECOBeaconRegion> regions) {
        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.stopMonitoringForRegion(region);
            } catch (RemoteException e) {
                Log.i("RecoMonitoringActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RecoMonitoringActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    private void unbind() {
        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.i("RecoMonitoringActivity", "Remote Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceFail(RECOErrorCode errorCode) {
        Log.i("onServiceFail", "onServiceFail");
        return;
    }

    @Override
    public void monitoringDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
        Log.i("monitoring", "monitoringDidFailForRegion");
        return;
    }
    private void popupNotification(String msg) {
        Log.i("BackMonitoringService", "popupNotification()");
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(new Date());
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_icon_noti)
                .setContentTitle(msg + " " + currentTime)
                .setContentText(msg);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        builder.setStyle(inboxStyle);

        if (nm != null) {
            nm.notify(notificationID, builder.build());
            notificationID = (notificationID - 1) % 1000 + 9000;
        }
    }
}
