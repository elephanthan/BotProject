package com.worksmobile.android.botproject.feature.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;

/**
 * Created by user on 2018. 5. 8..
 */

public class SetnotiDialogFragment extends DialogFragment {

    public static final String SETNOTI_DIALOG_KEY = "SetnotiDialog";

    public static SetnotiDialogFragment newInstance(String userId) {
        SetnotiDialogFragment dialog = new SetnotiDialogFragment();
        Bundle args = new Bundle();
        args.putString(SETNOTI_DIALOG_KEY, userId);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = 550;
        params.height = 450;
        getDialog().getWindow().setAttributes(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_simple, container);
        TextView textViewUserId = view.findViewById(R.id.dialog_textview_userid);

        if (getArguments() != null) {
            textViewUserId.setText(getArguments().getString(SETNOTI_DIALOG_KEY));
        }

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

}