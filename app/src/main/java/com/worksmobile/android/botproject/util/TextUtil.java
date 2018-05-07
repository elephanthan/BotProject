package com.worksmobile.android.botproject.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by user on 2018. 5. 7..
 */

public class TextUtil {

    public static String getResizedText(Context context, TextView titleTextView, String title){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int widthPx = metrics.widthPixels;
        float density = metrics.density;
        float widthDp = widthPx / density;

        float titleTextViewWidthDp = widthDp;
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            titleTextViewWidthDp *= 0.9f;
        } else {
            titleTextViewWidthDp *= 0.7f;
        }

        float textSize = titleTextView.getTextSize() / metrics.scaledDensity;;
        float textMaxLen = titleTextViewWidthDp / textSize;

        String titleText = title;
        if(title!=null) {
            String ellipse = "...";
            if(title.length() > textMaxLen) {
                titleText = title.substring(0, (int) textMaxLen - ellipse.length()).concat(ellipse);
            }
        }

        return titleText;
    }

}
