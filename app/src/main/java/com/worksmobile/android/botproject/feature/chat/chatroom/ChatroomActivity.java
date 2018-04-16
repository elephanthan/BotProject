package com.worksmobile.android.botproject.feature.chat.chatroom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.feature.SingleFragmentActivity;

public class ChatroomActivity extends SingleFragmentActivity {

    private ViewPager viewPager;
    private View mainView;

    private static final String EXTRA_CHATROOM_ID =
            "chatroom_id";

    public static Intent newIntent(Context context, long chatroomId){
        Intent intent = new Intent(context, ChatroomActivity.class);
        intent.putExtra(EXTRA_CHATROOM_ID, chatroomId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.findViewById(R.id.fragment_container).setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return true;
            }
        });

        this.findViewById(R.id.fragment_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("erwrwer","ewrwrewr");
            }
        });
    }

    @Override
    protected Fragment createFragment() {
//        this.findViewById(R.id.fragment_container).setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                hideKeyboard();
//                return true;
//            }
//        });
//        this.findViewById(R.id.fragment_container).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("erwrwer","ewrwrewr");
//            }
//        });

        long chatroomId = (long) getIntent().getLongExtra(EXTRA_CHATROOM_ID, 0);
        return ChatroomFragment.newInstance(chatroomId);
    }

    public void hideKeyboard() {
        Activity activity = getParent();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
