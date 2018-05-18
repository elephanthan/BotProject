package com.worksmobile.android.botproject.beacon;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECOMonitoringListener;
import com.perples.recosdk.RECOServiceConnectListener;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.util.BeaconUtil;
import com.worksmobile.android.botproject.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.Collection;

import static com.worksmobile.android.botproject.feature.splash.SplashActivity.retrofitClient;

/**
 * RECOBackgroundMonitoringService is to monitor regions in the background.
 *
 * RECOBackgroundMonitoringService는 백그라운드에서 monitoring을 수행합니다.
 */
public class MonitoringService extends Service implements RECOMonitoringListener, RECOServiceConnectListener{

    public static final String TAG = MonitoringService.class.getSimpleName();

    private RECOBeaconManager recoManager;
    private ArrayList<RECOBeaconRegion> regions;

    @Override
    public void onCreate() {
        Log.i("BackMonitoringService", "onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("BackMonitoringService", "onStartCommand()");

        recoManager = RECOBeaconManager.getInstance(getApplicationContext(), SettingInfo.SCAN_RECO_ONLY, SettingInfo.ENABLE_BACKGROUND_RANGING_TIMEOUT);
        this.bindRECOService();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("BackMonitoringService", "onDestroy()");
        this.tearDown();
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("BackMonitoringService", "onTaskRemoved()");
        super.onTaskRemoved(rootIntent);
    }

    private void bindRECOService() {
        Log.i("BackMonitoringService", "bindRECOService()");

        regions = new ArrayList<RECOBeaconRegion>();
        this.generateBeaconRegion();

        recoManager.setMonitoringListener(this);
        recoManager.bind(this);
    }

    private void generateBeaconRegion() {
        Log.i("BackMonitoringService", "generateBeaconRegion()");

        RECOBeaconRegion recoRegion;

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_COMMUTE_A, SettingInfo.RECO_IDENTIFIER_COMMUTE_A);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_COMMUTE_B, SettingInfo.RECO_IDENTIFIER_COMMUTE_B);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_INTRODUCE_LOCATION, SettingInfo.RECO_MINOR_INTRODUCE_LOCATION_1, SettingInfo.RECO_IDENTIFIER_INTRODUCE_LOCATION_1);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_INTRODUCE_LOCATION, SettingInfo.RECO_MINOR_INTRODUCE_LOCATION_2, SettingInfo.RECO_IDENTIFIER_INTRODUCE_LOCATION_2);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_INTRODUCE_LOCATION, SettingInfo.RECO_MINOR_INTRODUCE_LOCATION_3, SettingInfo.RECO_IDENTIFIER_INTRODUCE_LOCATION_3);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_INTRODUCE_LOCATION, SettingInfo.RECO_MINOR_INTRODUCE_LOCATION_4, SettingInfo.RECO_IDENTIFIER_INTRODUCE_LOCATION_4);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);
    }

    private void startMonitoring() {
        Log.i("BackMonitoringService", "startMonitoring()");

        recoManager.setScanPeriod(SettingInfo.mScanDuration);
        recoManager.setSleepPeriod(SettingInfo.mSleepDuration);

        for(RECOBeaconRegion region : regions) {
            try {
                recoManager.startMonitoringForRegion(region);
            } catch (RemoteException e) {
                Log.e("BackMonitoringService", "RemoteException has occured while executing RECOManager.startMonitoringForRegion()");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.e("BackMonitoringService", "NullPointerException has occured while executing RECOManager.startMonitoringForRegion()");
                e.printStackTrace();
            }
        }
    }

    private void stopMonitoring() {
        Log.i("BackMonitoringService", "stopMonitoring()");

        for(RECOBeaconRegion region : regions) {
            try {
                recoManager.stopMonitoringForRegion(region);
            } catch (RemoteException e) {
                Log.e("BackMonitoringService", "RemoteException has occured while executing RECOManager.stopMonitoringForRegion()");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.e("BackMonitoringService", "NullPointerException has occured while executing RECOManager.stopMonitoringForRegion()");
                e.printStackTrace();
            }
        }
    }

    private void tearDown() {
        Log.i("BackMonitoringService", "tearDown()");
        this.stopMonitoring();

        try {
            recoManager.unbind();
        } catch (RemoteException e) {
            Log.e("BackMonitoringService", "RemoteException has occured while executing unbind()");
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnect() {
        Log.i("BackMonitoringService", "onServiceConnect()");
        this.startMonitoring();
        //Write the code when RECOBeaconManager is bound to RECOBeaconService
    }

    @Override
    public void didDetermineStateForRegion(RECOBeaconRegionState state, RECOBeaconRegion region) {
        Log.i("BackMonitoringService", "didDetermineStateForRegion()");
        //Write the code when the state of the monitored region is changed
    }

    @Override
    public void didEnterRegion(RECOBeaconRegion region, Collection<RECOBeacon> beacons) {
        Log.i(TAG, "didRangeBeaconsInRegion() region: " + region.getUniqueIdentifier() + ", number of beacons ranged: " + beacons.size());

        String employeeNumber = SharedPrefUtil.getStringPreference(this, SharedPrefUtil.SHAREDPREF_KEY_USERID);

        if (beacons.size() == 0){
            return;
        }

        String result = BeaconUtil.checkIsSentRecently(this, region);

        if(!result.equals("failed")) {
            for (RECOBeacon beacon : beacons) {
                retrofitClient.sendBeaconEvent(employeeNumber, beacon.getProximityUuid(), beacon.getMajor(), beacon.getMinor(), 1, beacon.getAccuracy(), new ApiRepository.RequestVoidCallback() {
                    @Override
                    public void success() {
                        SharedPrefUtil.setMiliSecondsPreference(MonitoringService.this, result);
                        BeaconUtil.popupNotification(MonitoringService.this, region.getUniqueIdentifier() + "입장");
                    }

                    @Override
                    public void error(Throwable throwable) {

                    }
                });
            }
        }
    }

    @Override
    public void didExitRegion(RECOBeaconRegion region) {
        Log.i("BackMonitoringService", "didExitRegion() - " + region.getUniqueIdentifier());
//        this.popupNotification("Outside of " + region.getUniqueIdentifier());
    }

    @Override
    public void didStartMonitoringForRegion(RECOBeaconRegion region) {
        Log.i("BackMonitoringService", "didStartMonitoringForRegion() - " + region.getUniqueIdentifier());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onServiceFail(RECOErrorCode errorCode) {
        return;
    }

    @Override
    public void monitoringDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
        return;
    }
}
