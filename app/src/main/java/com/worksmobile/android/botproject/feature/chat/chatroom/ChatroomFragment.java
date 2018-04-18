package com.worksmobile.android.botproject.feature.chat.chatroom;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.feature.chat.chatroomlist.ChatroomLab;
import com.worksmobile.android.botproject.feature.dialog.UserinfoDialogFragment;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class ChatroomFragment extends Fragment implements ChatroomClickListener {

    private static final String ARG_CHATROOM_ID = "chatroom_id";

    @BindView(R.id.button_chatroom_send)
    Button btnChatroomSend;
    @BindView(R.id.edittext_chatroom)
    EditText editTextChatroom;
    @BindView(R.id.indoor_recycler_view)
    RecyclerView messageRecyclerView;

    private View view;

    private MessageAdapter messageAdapter;

    private Chatroom chatroom;
    private List<Message> messages;

    public static ChatroomFragment newInstance(long chatroomId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CHATROOM_ID, chatroomId);
        ChatroomFragment fragment = new ChatroomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long chatroomId = (long) getArguments().getSerializable(ARG_CHATROOM_ID);
        chatroom = ChatroomLab.get().getChatroom(chatroomId);

        //TODO 채팅방 아이디로 메시지 내역들을 가져옴
        messages = MessageLab.get().getMessages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chatroom, container, false);
        ButterKnife.bind(this, view);

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateIndoorUI();

        return view;
    }

    private void updateIndoorUI() {
        MessageLab messageLab = MessageLab.get();
        List<Message> messages = messageLab.getMessages();

        if (messageAdapter == null) {
            messageAdapter = new MessageAdapter(getActivity(), messages, this);
            messageRecyclerView.setAdapter(messageAdapter);
        } else {

        }
    }

    @Override
    public void onMsgClick(int position) {
        Toast.makeText(getActivity(), messages.get(position).getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileClick(int position) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        UserinfoDialogFragment dialogFragment = UserinfoDialogFragment.newInstance(messages.get(position).getSenderId());
        dialogFragment.show(fm, "fragment_dialog_test");
    }


    @Override
    public void onHolderClick() {
        hideKeyboardFrom(getContext(), view);
    }

    @OnClick(R.id.button_chatroom_send)
    public void onChatroomSendClick() {
        String strText = editTextChatroom.getText().toString();


        if (!strText.equals("")){
            //Message msg = new Message(strText, Message.VIEW_TYPE_MESSAGE_SENT);
            Message msg = new Message(strText, messages.size()%2+1, "shawty");
            messages.add(msg);
            messageAdapter.notifyDataSetChanged();

            Log.d("msg model : ", msg.toString());

            editTextChatroom.setText("");

            messageRecyclerView.smoothScrollToPosition(messages.size());
        }
    }

    @OnTouch(R.id.indoor_recycler_view)
    public boolean onRecyclerViewTouch(){
        hideKeyboardFrom(getContext(), view);
        return false;
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
