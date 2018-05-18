package com.worksmobile.android.botproject.feature.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.model.User;

import static com.worksmobile.android.botproject.feature.splash.SplashActivity.retrofitClient;

/**
 * Created by user on 2018. 4. 5..
 */

public class UserinfoDialogFragment extends DialogFragment {

    public static final String USERINFO_DIALOG_KEY = "UserinfoDialog";

    public static UserinfoDialogFragment newInstance(String userId) {
        UserinfoDialogFragment dialog = new UserinfoDialogFragment();
        Bundle args = new Bundle();
        args.putString(USERINFO_DIALOG_KEY, userId);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setAttributes(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_userinfo, container);
        ImageView imageViewProfile = view.findViewById(R.id.dialog_imageview_profile);
        TextView textViewUserId = view.findViewById(R.id.dialog_textview_userid);
        TextView textViewNickname = view.findViewById(R.id.dialog_textview_nickname);

        String employeeNumber = getArguments().getString(USERINFO_DIALOG_KEY);

        retrofitClient.getUser(employeeNumber, new ApiRepository.RequestUserCallback() {
            @Override
            public void success(User user) {
                if (user != null) {
                    Glide.with(getActivity()).load(user.getProfile()).placeholder(R.drawable.ic_icon_man).error(R.drawable.ic_icon_man).into(imageViewProfile);
                    textViewUserId.setText(user.getId());
                    textViewNickname.setText(user.getName());
                }
            }

            @Override
            public void error(Throwable throwable) {
                Toast.makeText(getActivity(), R.string.alert_network_connection_fail, Toast.LENGTH_SHORT).show();
            }
        });

        if (getArguments() != null) {
            textViewUserId.setText(getArguments().getString(USERINFO_DIALOG_KEY));
        }

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

}