package com.worksmobile.android.botproject.feature.chat.newchat;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.worksmobile.android.botproject.R;

/**
 * Created by user on 2018. 4. 25..
 */

public class NewchatPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private int tabSize;

    public NewchatPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.tabSize = tabSize;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NewchatUsersFragment();
        } else {
            return new NewchatBotsFragment();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.newchat_users);
            case 1:
                return mContext.getString(R.string.newchat_bots);
            default:
                return null;
        }
    }


}
