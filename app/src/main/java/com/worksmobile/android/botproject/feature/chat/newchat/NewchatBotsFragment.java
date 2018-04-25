package com.worksmobile.android.botproject.feature.chat.newchat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;

/**
 * Created by user on 2018. 4. 25..
 */

public class NewchatBotsFragment extends Fragment {

    public static NewchatBotsFragment newInstance() {
        Bundle args = new Bundle();
        NewchatBotsFragment fragment = new NewchatBotsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newchat, container, false);
        TextView textView = (TextView) view.findViewById(R.id.test_text);
        textView.setText("NewchatBotsFragment");
        return view;
    }

}
