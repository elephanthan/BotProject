package com.worksmobile.android.botproject.feature.chat.newchat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.feature.chat.chatroom.UserLab;
import com.worksmobile.android.botproject.model.Invitable;

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
    List<Invitable> invitables = new ArrayList<Invitable>();

    public static UserListFragment newInstance() {
        Bundle args = new Bundle();
        UserListFragment fragment = new UserListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newchat, container, false);
        ButterKnife.bind(this, view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        UserLab userLab = UserLab.get();
        List<? extends Invitable> users = userLab.getUsers();
        invitables = new ArrayList<>(users);

        if(userAdapter == null){
            userAdapter = new InvitableAdapter(getActivity(), invitables, this);
            userRecyclerView.setAdapter(userAdapter);
        }
    }


    @Override
    public void onHolderClick(int position) {
        Toast.makeText(getActivity(),invitables.get(position).getName(),Toast.LENGTH_SHORT).show();
    }
}
