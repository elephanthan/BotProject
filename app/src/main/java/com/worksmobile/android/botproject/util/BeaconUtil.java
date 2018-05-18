package com.worksmobile.android.botproject.util;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.perples.recosdk.RECOBeaconRegion;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.beacon.SettingInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BeaconUtil {
    private static int notificationID = 9999;

    public static boolean checkIsSentRecently(Context context, RECOBeaconRegion region) {
        String key = region.getProximityUuid().substring(0,7).concat("_");

        if (region.getMajor() != null) {
            key = key.concat(region.getMajor().toString());
        }
        if (region.getMinor() != null) {
            key = key.concat(region.getMinor().toString());
        }

        long millisecondsNow = new Date().getTime();
        long minutesNow = ((millisecondsNow / 1000) / 60);

        long millisecondsRecently = SharedPrefUtil.getLongPreference(context, key);

        long minutesRecently = ((millisecondsRecently / 1000) / 60);

        //if get default value return to confirm api request
        if (millisecondsRecently < 0) {
            Log.d("### Confirm :", "First Request");
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
            Log.d("### Reject : < 30min", region.getUniqueIdentifier() + new Date(millisecondsNow) + " <------> " + new Date(millisecondsRecently));
            return true;
        }
        else {
            Log.d("### Confirm : > 30min", region.getUniqueIdentifier() + new Date(millisecondsNow) + " <------> " + new Date(millisecondsRecently));
            SharedPrefUtil.setMiliSecondsPreference(context, key);
            return false;
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
}
