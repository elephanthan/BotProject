package com.worksmobile.android.botproject.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by user on 2018. 5. 9..
 */

public class ViewUtil {

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static ViewGroup getActionBar(View view) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;

                if (viewGroup instanceof android.support.v7.widget.Toolbar) {
                    return viewGroup;
                }

                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    ViewGroup actionBar = getActionBar(viewGroup.getChildAt(i));

                    if (actionBar != null) {
                        return actionBar;
                    }
                }
            }
        } catch (Exception e) {
        }

        return null;
    }

    public static String getResizedTextViewText(Context context, TextView titleTextView, String title){

        if (title == null) {
            return null;
        }

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int widthPx = metrics.widthPixels;
        float density = metrics.density;
        float widthDp = widthPx / density;

        float titleTextViewWidthDp = widthDp;
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            titleTextViewWidthDp *= 0.8f;
        } else {
            titleTextViewWidthDp *= 0.9f;
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
