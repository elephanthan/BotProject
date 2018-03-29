package com.worksmobile.android.botproject;

import android.support.v4.app.Fragment;

public class ChatroomActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ChatroomFragment();
    }
    
}
