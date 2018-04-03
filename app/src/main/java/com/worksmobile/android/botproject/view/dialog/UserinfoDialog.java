package com.worksmobile.android.botproject.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.worksmobile.android.botproject.R;

/**
 * Created by user on 2018. 4. 3..
 */

public class UserinfoDialog extends Dialog {
    public UserinfoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_userinfo);
    }
}
