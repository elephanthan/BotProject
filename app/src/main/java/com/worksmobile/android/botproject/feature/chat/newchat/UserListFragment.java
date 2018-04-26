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
import com.worksmobile.android.botproject.feature.chat.chatroom.UserLab;
import com.worksmobile.android.botproject.model.Talker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018. 4. 25..
 */

public class UserListFragment extends Fragment implements InvitableClickListener {

    @BindView(R.id.newchat_recycler_view)
    RecyclerView userRecyclerView;
    private InvitableAdapter userAdapter;
    List<Talker> talkers = new ArrayList<Talker>();

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_chatting:
                List<Talker> checkedList = getCheckedTalkers();
                Toast.makeText(getActivity(), checkedList.size()+"명 초대!", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getActivity(), ChatroomActivity.class));
            default:
                break;
        }

        return false;
    }

    private void updateUI() {
        UserLab userLab = UserLab.get();
        List<? extends Talker> users = userLab.getUsers();
        talkers = new ArrayList<>(users);

        if (userAdapter == null) {
            userAdapter = new InvitableAdapter(getActivity(), talkers, this);
            userRecyclerView.setAdapter(userAdapter);
        }
    }

    @Override
    public void onHolderClick(int position) {
    }

    @Override
    public void onCheckBoxClick(int position) {
        Talker talker = talkers.get(position);
        talker.setChecked(!talker.isChecked());

        int checkedSize = getCheckedTalkers().size();


        if (checkedSize > 0) {
            if (!checkMenuItem.isEnabled()) {
                checkMenuItem.setEnabled(true);
            }
        } else {
            checkMenuItem.setEnabled(false);
        }
    }

    private List<Talker> getCheckedTalkers() {
        List<Talker> result = new ArrayList<Talker>();
        for (Talker t : talkers) {
            if (t.isChecked()) {
                result.add(t);
            }
        }
        return result;
    }
}