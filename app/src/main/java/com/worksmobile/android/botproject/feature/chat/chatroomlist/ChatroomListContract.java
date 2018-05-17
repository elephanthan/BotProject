package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import com.worksmobile.android.botproject.BasePresenter;
import com.worksmobile.android.botproject.BaseView;
import com.worksmobile.android.botproject.model.Chatroom;

import java.util.List;

public interface ChatroomListContract {
    interface View extends BaseView<ChatroomListContract.Presenter> {
        void showChatrooms();

        void showNoChatrooms();

        void moveToChatroom(long chatroomId, int chatroomType);
    }

    interface Presenter extends BasePresenter {
        void loadChatrooms(String userId);

        void enterChatroom(int position);
    }

    interface AdapterView {
        void refresh(List<Chatroom> chatrooms);
    }
}
