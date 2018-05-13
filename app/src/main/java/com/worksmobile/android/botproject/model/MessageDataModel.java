package com.worksmobile.android.botproject.model;

import java.util.List;

public interface MessageDataModel {
    List<Message> setMessagesByUserId(List<Message> messageList, String userId);

    List<Message> makeType3Message(List<Message> messageList);
}
