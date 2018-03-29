package com.worksmobile.android.botproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChatroomFragment extends Fragment {

    private static final String ARG_CHATROOM_ID = "chatroom_id";

    private Chatroom chatroom;

    private TextView titleTextView;

    public static ChatroomFragment newInstance(long chatroomId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHATROOM_ID, chatroomId);
        ChatroomFragment fragment = new ChatroomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        long chatroomId = (long) getArguments().getSerializable(ARG_CHATROOM_ID);
        chatroom = ChatroomLab.get(getActivity()).getChatroom(chatroomId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_chatroom, container, false);

        titleTextView = (TextView) v.findViewById(R.id.chat_room_text_title);
        titleTextView.setText(chatroom.getTitle());

        return v;
    }
}
