package com.worksmobile.android.botproject.feature.Chat.Chatroom;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.feature.dialog.UserinfoDialogFragment;

/**
 * Created by user on 2018. 4. 4..
 */

class ChatroomClickListnerImpl implements ChatroomClickListener {

    Context context;
    Message msg;

    public ChatroomClickListnerImpl(Context context, Message msg){
        this.context = context;
        this.msg = msg;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_message_profile :
//                UserinfoDialog dialog = new UserinfoDialog(context);
//                dialog.show();
                FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
                UserinfoDialogFragment dialogFragment = new UserinfoDialogFragment();
                dialogFragment.show(fm, "fragment_dialog_test");
                break;
            case R.id.text_message_body :
                Toast.makeText(context, msg.getText(), Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }
}
