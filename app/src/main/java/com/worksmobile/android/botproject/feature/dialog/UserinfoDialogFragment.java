package com.worksmobile.android.botproject.feature.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;

/**
 * Created by user on 2018. 4. 5..
 */

public class UserinfoDialogFragment extends DialogFragment {

    public static final String UserinfoDialogKey = "UserinfoDialog";

    public static UserinfoDialogFragment newInstance(String userId) {
        UserinfoDialogFragment dialog = new UserinfoDialogFragment();
        Bundle args = new Bundle();
        args.putString(UserinfoDialogKey, userId);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_userinfo, container);
        TextView textViewUserId = view.findViewById(R.id.userdialog_textview_userid);
        if(getArguments()!=null) {
            textViewUserId.setText(getArguments().getString(UserinfoDialogKey));
        }
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

}