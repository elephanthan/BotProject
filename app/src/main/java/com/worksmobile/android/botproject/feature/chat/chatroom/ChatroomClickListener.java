package com.worksmobile.android.botproject.feature.chat.chatroom;

/**
 * Created by user on 2018. 4. 4..
 */

public interface ChatroomClickListener {
    void onMsgClick(int position);
    void onProfileClick(int position);
    void onHolderClick();
    void onMessageImageClick(int position);
}
