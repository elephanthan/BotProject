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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.feature.dialog.UserinfoDialogFragment;
import com.worksmobile.android.botproject.model.Chatroom;
import com.worksmobile.android.botproject.model.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatroomFragment extends Fragment implements ChatroomClickListener {

    private static final String ARG_CHATROOM_ID = "chatroom_id";

    @BindView(R.id.button_chatroom_send)
    Button btnChatroomSend;
    @BindView(R.id.edittext_chatroom)
    EditText editTextChatroom;
    @BindView(R.id.indoor_recycler_view)
    RecyclerView messageRecyclerView;

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
        //지금은 메시지 더미 데이터를 만듦
        messages = MessageLab.get().getMessages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatroom, container, false);
        ButterKnife.bind(this, view);

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateIndoorUI();

        return view;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        getActivity().findViewById(R.id.fragment_container).setOnTouchListener(new View.OnTouchListener()
////        {
////            @Override
////            public boolean onTouch(View dview, MotionEvent ev)
////            {
////                hideKeyboardFrom(getContext(), dview);
////                //ChatroomActivity.hideKeyboard(getActivity());
////                return true;
////            }
////        });
//    }

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

    @OnClick(R.id.button_chatroom_send)
    public void onChatroomSendClick() {
        Message msg = new Message(editTextChatroom.getText().toString(), Message.VIEW_TYPE_MESSAGE_SENT);
        messages.add(msg);
        messageAdapter.notifyDataSetChanged();

        Log.d("msg model : ", msg.toString());

        editTextChatroom.setText("");

    }

    public boolean dispatchTouchEvent(MotionEvent event) {

        View view = getActivity().getCurrentFocus();
        boolean ret = super.getActivity().dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getActivity().getCurrentFocus();
            int location[] = new int[2];
            w.getLocationOnScreen(location);
            float x = event.getRawX() + w.getLeft() - location[0];
            float y = event.getRawY() + w.getTop() - location[1];
            if (event.getAction() == MotionEvent.ACTION_DOWN
                    && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
