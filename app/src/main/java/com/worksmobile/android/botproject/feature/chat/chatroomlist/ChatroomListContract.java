package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import com.worksmobile.android.botproject.BasePresenter;
import com.worksmobile.android.botproject.BaseView;
import com.worksmobile.android.botproject.model.Chatroom;

import java.util.List;

public interface ChatroomListContract {
    interface View extends BaseView<ChatroomListContract.Presenter> {

        void showChatrooms(List<Chatroom> chatrooms);

    }

    interface Presenter extends BasePresenter {

        void loadChatrooms();

    }

    interface AdapterView {
        void refresh();
    }
}
