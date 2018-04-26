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
import com.worksmobile.android.botproject.model.Talker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018. 4. 25..
 */

public class BotListFragment extends Fragment implements InvitableClickListener {

    @BindView(R.id.newchat_recycler_view)
    RecyclerView userRecyclerView;
    private InvitableAdapter userAdapter;
    private List<Talker> talkers = new ArrayList<Talker>();

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_chatting:
                Talker talker = getCheckedTalker();
                Toast.makeText(getActivity(), talker.getName() + "과 시작", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getActivity(), ChatroomActivity.class));
            default:
                break;
        }

        return false;
    }

    private void updateUI() {
        BotLab botLab = BotLab.get();
        List<? extends Talker> bots = botLab.getBots();
        talkers = new ArrayList<>(bots);

        if (userAdapter == null) {
            userAdapter = new InvitableAdapter(getActivity(), talkers, this);
            userRecyclerView.setAdapter(userAdapter);
        }
    }

    @Override
    public void onHolderClick(int position) {
        Toast.makeText(getActivity(), talkers.get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckBoxClick(int position) {
        Talker preCheckedTalker = getCheckedTalker();
        if (talkers.get(position).equals(preCheckedTalker)) {
            talkers.get(position).setChecked(false);

            checkMenuItem.setEnabled(false);
        } else {
            if(preCheckedTalker != null){
                preCheckedTalker.setChecked(false);
            }
            talkers.get(position).setChecked(true);

            checkMenuItem.setEnabled(true);
        }
        userAdapter.notifyDataSetChanged();
    }

    private Talker getCheckedTalker() {
        for (Talker t : talkers) {
            if (t.isChecked()) {
                return t;
            }
        }
        //return new Talker();
        return null;
    }
}
