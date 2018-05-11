package com.worksmobile.android.botproject.feature.chat.chatroom;

import com.worksmobile.android.botproject.BasePresenter;
import com.worksmobile.android.botproject.BaseView;

public interface ChatroomContract {
    interface View extends BaseView<Presenter> {
        void refresh();
    }

    interface Presenter extends BasePresenter{
        void setView(View view);
    }
}