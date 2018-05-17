package com.worksmobile.android.botproject.feature.chat.newchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
import com.worksmobile.android.botproject.feature.chat.chatroom.ChatroomActivity;
import com.worksmobile.android.botproject.model.Bot;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Talker;
import com.worksmobile.android.botproject.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.worksmobile.android.botproject.feature.splash.SplashActivity.retrofitClient;

/**
 * Created by user on 2018. 4. 25..
 */

public class BotListFragment extends Fragment implements TalkerClickListener {

    @BindView(R.id.newchat_recycler_view)
    RecyclerView userRecyclerView;
    private TalkerAdapter talkerAdapter;
    private List<? extends Talker> talkers = new ArrayList<>();

    private MenuItem checkMenuItem;

    public static BotListFragment newInstance() {
        Bundle args = new Bundle();
        BotListFragment fragment = new BotListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newchat, container, false);
        ButterKnife.bind(this, view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        checkMenuItem = menu.getItem(0);
        checkMenuItem.setEnabled(false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (getCheckedTalker() != null) {
            checkMenuItem.setEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_ok:
                Talker checkedTalker = getCheckedTalker();

                String employeeNumber = SharedPrefUtil.getStringPreference(getActivity(), SharedPrefUtil.SHAREDPREF_KEY_USERID);;
                NewchatDataModel newchatDataModel = new NewchatDataModel(employeeNumber, checkedTalker);
                retrofitClient.startNewchat(newchatDataModel, new ApiRepository.RequestChatroomCallback() {
                    @Override
                    public void success(Chatroom chatroom) {
                        Log.i("chatroom", chatroom.toString());
                        Intent intent = ChatroomActivity.newIntent(BotListFragment.this.getActivity(), chatroom.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void error(Throwable throwable) {
                        Toast.makeText(getActivity(), R.string.alert_network_connection_fail, Toast.LENGTH_SHORT).show();
                    }
                });

            default:
                break;
        }

        return false;
    }

    private void updateUI() {
        retrofitClient.getTalkers(new ApiRepository.RequestTalkerCallback() {
            @Override
            public void success(TalkerDataModel talkerDataModel) {
                talkerDataModel.setUsers(talkerDataModel.getUsers());
                talkerDataModel.setBots((ArrayList<Bot>) talkerDataModel.getBots());
                talkers = talkerDataModel.getTalkers(Talker.TALKER_TYPE_BOT);

                if (talkerAdapter == null) {
                    talkerAdapter = new TalkerAdapter(getActivity(), talkers, BotListFragment.this);
                    userRecyclerView.setAdapter(talkerAdapter);
                }
            }

            @Override
            public void error(Throwable throwable) {

            }
        });
    }

    @Override
    public void onHolderClick(int position) {
        onCheckBoxClick(position);
    }

    @Override
    public void onCheckBoxClick(int position) {
        Talker preCheckedTalker = getCheckedTalker();
        if (talkers.get(position).equals(preCheckedTalker)) {
            talkers.get(position).setChecked(false);
        } else {
            if(preCheckedTalker != null){
                preCheckedTalker.setChecked(false);
            }
            talkers.get(position).setChecked(true);
        }
        talkerAdapter.notifyDataSetChanged();
        getActivity().invalidateOptionsMenu();
    }

    private Talker getCheckedTalker() {
        if (talkers != null) {
            for (Talker talker : talkers) {
                if (talker.isChecked()) {
                    return talker;
                }
            }
            return null;
        }
        return null;
    }
}
