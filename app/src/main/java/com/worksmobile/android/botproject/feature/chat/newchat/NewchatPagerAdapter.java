package com.worksmobile.android.botproject.feature.chat.newchat;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.worksmobile.android.botproject.R;

/**
 * Created by user on 2018. 4. 25..
 */

public class NewchatPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private int tabSize;

    public NewchatPagerAdapter(Context context, int tabSize) {

        super(((NewchatActivity)context).getSupportFragmentManager());
        this.context = context;
        this.tabSize = tabSize;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UserListFragment();
            case 1:
                return new BotListFragment();
            default:
                return new UserListFragment();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return tabSize;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return context.getString(R.string.newchat_users);
            case 1:
                return context.getString(R.string.newchat_bots);
            default:
                return context.getString(R.string.newchat_users);
        }
    }


}
