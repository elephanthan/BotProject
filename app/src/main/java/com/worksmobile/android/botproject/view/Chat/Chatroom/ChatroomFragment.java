package com.worksmobile.android.botproject.view.Chat.Chatroom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Message;

import java.util.List;

public class ChatroomFragment extends Fragment {

    private static final String ARG_CHATROOM_ID = "chatroom_id";

    private RecyclerView messageRecyclerView;
    private MessageAdapter messageAdapter;

    private Chatroom chatroom;
    private List<Message> messages;

    public static ChatroomFragment newInstance(long chatroomId){
        Bundle args = new Bundle();
        args.putLong(ARG_CHATROOM_ID, chatroomId);
        ChatroomFragment fragment = new ChatroomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        long chatroomId = (long) getArguments().getSerializable(ARG_CHATROOM_ID);
        chatroom = ChatroomLab.get().getChatroom(chatroomId);

        //TODO 채팅방 아이디로 메시지 내역들을 가져옴
        //지금은 메시지 더미 데이터를 만듦
        messages = MessageLab.get().getMessages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_chatroom, container, false);

//        titleTextView = (TextView) v.findViewById(R.id.chat_room_text_title);
//        titleTextView.setText(chatroom.getTitle());
        messageRecyclerView = (RecyclerView) v.findViewById(R.id.indoor_recycler_view);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateIndoorUI();

        return v;
    }

    private void updateIndoorUI(){
        MessageLab messageLab = MessageLab.get();
        List<Message> messages = messageLab.getMessages();

        if(messageAdapter == null){
            messageAdapter = new MessageAdapter(getActivity(), messages);
            messageRecyclerView.setAdapter(messageAdapter);
        }else{

        }
    }
}
