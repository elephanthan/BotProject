package com.worksmobile.android.botproject.feature.chat.newchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Chatroom;

public class NewchatActivity extends AppCompatActivity {

    final public String EXTRA_CHATROOMID = "chatroom_id";
    final public String EXTRA_CHATROOM_TYPE = "chatroom_type";
    private long chatroomId = -1;
    private int chatroomType = Chatroom.CHATROOM_TYPE_USER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newchat);

        getSupportActionBar().setTitle(R.string.barname_newchat);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.newchat_users));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.newchat_bots));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new NewchatPagerAdapter(this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.triggers_ok, menu);
        return true;
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        if (this.chatroomId > 0) {
            intent.putExtra(EXTRA_CHATROOMID, this.chatroomId);
            intent.putExtra(EXTRA_CHATROOM_TYPE, this.chatroomType);
            setResult(RESULT_OK, intent);

            super.finish();
        } else {
            intent.putExtra(EXTRA_CHATROOMID, this.chatroomId);
            setResult(RESULT_CANCELED, intent);

            super.finish();
        }
    }

    public void setChatroomId(long chatroomId) {
        this.chatroomId = chatroomId;
    }

    public void setChatroomType(int chatroomType) {
        this.chatroomType = chatroomType;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
