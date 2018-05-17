package com.worksmobile.android.botproject.feature.chat.newchat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.api.ApiRepository;
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

public class UserListFragment extends Fragment implements TalkerClickListener {

    @BindView(R.id.newchat_recycler_view)
    RecyclerView userRecyclerView;
    private TalkerAdapter talkerAdapter;
    List<? extends Talker> talkers = new ArrayList<>();

    private MenuItem checkMenuItem;

    public static UserListFragment newInstance() {
        Bundle args = new Bundle();
        UserListFragment fragment = new UserListFragment();
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
        int checkedSize = getCheckedTalkers().size();
        if (checkedSize > 0) {
            checkMenuItem.setEnabled(true);
        } else {
            checkMenuItem.setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_ok:
                List<Talker> checkedList = getCheckedTalkers();

                String employeeNumber = SharedPrefUtil.getStringPreference(getActivity(), SharedPrefUtil.SHAREDPREF_KEY_USERID);;
                NewchatDataModel newchatDataModel = new NewchatDataModel(employeeNumber, checkedList);
                retrofitClient.startNewchat(newchatDataModel, new ApiRepository.RequestChatroomCallback() {
                    @Override
                    public void success(Chatroom chatroom) {
                        ((NewchatActivity)getActivity()).setChatroomId(chatroom.getId());
                        ((NewchatActivity)getActivity()).setChatroomType(chatroom.getChatroomType());
                        getActivity().finish();
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
                String employeeNumber = SharedPrefUtil.getStringPreference(getActivity(), SharedPrefUtil.SHAREDPREF_KEY_USERID);
                talkers = talkerDataModel.getUsersExceptId(employeeNumber);

                if (talkerAdapter == null) {
                    talkerAdapter = new TalkerAdapter(getActivity(), talkers, UserListFragment.this);
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
        //TODO : clean code
        talkerAdapter.updateCheckBox(position);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCheckBoxClick(int position) {
        Talker talker = talkers.get(position);
        talker.setChecked(!talker.isChecked());
        getActivity().invalidateOptionsMenu();
    }

    private List<Talker> getCheckedTalkers() {
        List<Talker> result = new ArrayList<>();
        if (talkers != null) {
            for (Talker talker : talkers) {
                if (talker.isChecked()) {
                    result.add(talker);
                }
            }
        }
        return result;
    }
}
