package com.worksmobile.android.botproject.util;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.perples.recosdk.RECOBeaconRegion;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.beacon.SettingInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BeaconUtil {
    private static int notificationID = 9999;
    private static int BEACON_UUID_KEY_LEN = 7;

    public static boolean checkIsSentRecently(Context context, RECOBeaconRegion region) {
        String key = getKey(region);

        long millisecondsNow = new Date().getTime();
        long minutesNow = ((millisecondsNow / 1000) / 60);

        long millisecondsRecently = SharedPrefUtil.getLongPreference(context, key);

        long minutesRecently = ((millisecondsRecently / 1000) / 60);

        //if get default value return to confirm api request
        if (millisecondsRecently < 0) {
            Log.i("### Confirm :", "First Request");
            SharedPrefUtil.setMiliSecondsPreference(context, key);
            return false;
        }

        int sendRequestTimeGap;
        if (region.getMajor() == SettingInfo.RECO_MAJOR_INTRODUCE_LOCATION) {
           sendRequestTimeGap = SettingInfo.TIME_GAP_INTRODUCE_LOCATION;
        } else {
            sendRequestTimeGap = SettingInfo.TIME_GAP_INTRODUCE_COMMUTE;
        }

        if(minutesNow - minutesRecently < sendRequestTimeGap) {
            Log.i("Reject : < threshold", region.getUniqueIdentifier() + new Date(millisecondsNow) + " <------> " + new Date(millisecondsRecently));
            return true;
        }
        else {
            Log.i("Confirm : > threshold", region.getUniqueIdentifier() + new Date(millisecondsNow) + " <------> " + new Date(millisecondsRecently));
            SharedPrefUtil.setMiliSecondsPreference(context, key);
            return false;
        }
    }

    public static void resetBeaconHistory(Context context, String botId) {
        List<RECOBeaconRegion> regions = new ArrayList<>();

        RECOBeaconRegion recoRegion;

        if(botId.equals("1")) {
            recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_COMMUTE_A, SettingInfo.RECO_IDENTIFIER_COMMUTE_A);
            recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
            regions.add(recoRegion);

            recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_COMMUTE_B, SettingInfo.RECO_IDENTIFIER_COMMUTE_B);
            recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
            regions.add(recoRegion);
        }

        else if(botId.equals("2")) {
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
        } else {
            return;
        }

        for (RECOBeaconRegion region : regions) {
            String key = getKey(region);

            Log.i("reset history : ", "New created");
            SharedPrefUtil.resetMiliSecondsPreference(context, key);
        }

    }

    public static void popupNotification(Context context, String msg) {
        Log.i("BackMonitoringService", "popupNotification()");
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(new Date());
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_icon_noti)
                .setContentTitle(msg + " " + currentTime)
                .setContentText(msg);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        builder.setStyle(inboxStyle);

        if (nm != null) {
            nm.notify(notificationID, builder.build());
            notificationID = (notificationID - 1) % 1000 + 9000;
        }
    }

    private static String getKey(RECOBeaconRegion region) {
        String key = region.getProximityUuid().substring(0, BEACON_UUID_KEY_LEN).concat("_");

        if (region.getMajor() != null) {
            key = key.concat(region.getMajor().toString());
        }
        if (region.getMinor() != null) {
            key = key.concat(region.getMinor().toString());
        }

        return key;
    }
}
