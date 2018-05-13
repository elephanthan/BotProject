package com.worksmobile.android.botproject.feature.chat.chatroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.model.MessageDataModel;
import com.worksmobile.android.botproject.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018. 3. 30..
 */

public class MessageAdapter extends RecyclerView.Adapter implements MessageDataModel{
    private LayoutInflater inflater;
    private List<Message> messages = new ArrayList<>();
    private List<User> users;
    private ChatroomClickListener listner;

    public MessageAdapter() {

    }

    public MessageAdapter(Context context, @NonNull List<Message> messages) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messages = messages;
        this.users = UserLab.get().getUsers();
    }

    public MessageAdapter(Context context, @NonNull List<Message> messages, ChatroomClickListener listener) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messages = messages;
        this.users = UserLab.get().getUsers();
        this.listner = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        final RecyclerView.ViewHolder holder;

        switch (viewType){
            case Message.VIEW_TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.item_message_sent, parent, false);
                holder = new SentMessageHolder(view, listner);
                break;
            case Message.VIEW_TYPE_MESSAGE_RECEIVED:
                view = inflater.inflate(R.layout.item_message_received, parent, false);
                holder = new ReceivedMessageHolder(view, listner);
                break;
            default:
                view = inflater.inflate(R.layout.item_message_sent, parent, false);
                holder = new SentMessageHolder(view, listner);
        }
        return holder;
    }
    //TODO Make Third Holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message msg = messages.get(position);
        switch (holder.getItemViewType()) {
            case Message.VIEW_TYPE_MESSAGE_SENT:
                 ((SentMessageHolder) holder).bindMessage(msg);
                 break;
            case Message.VIEW_TYPE_MESSAGE_RECEIVED:
                 ((ReceivedMessageHolder) holder).bindMessage(msg);
                 break;
             default:
                 ((SentMessageHolder) holder).bindMessage(msg);
                 break;
        }
    }

    @Override
    public int getItemCount() {
        if(messages == null) {
            return 0;
        }
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = messages.get(position);
        return msg.getType();
    }

    @Override
    public List<Message> setMessagesByUserId(List<Message> messageList, String userId) {
        for (Message msg : messageList) {
            if (msg.getSenderId().equals(userId)) {
                msg.setType(Message.VIEW_TYPE_MESSAGE_SENT);
            } else {
                msg.setType(Message.VIEW_TYPE_MESSAGE_RECEIVED);
            }
        }
        return messageList;
    }

    @Override
    public List<Message> makeType3Message(List<Message> messageList) {
        List<Message> messages = new ArrayList<>(messageList);
//        System.out.println("messages size : " + messages.size());
        List<Pair<Integer, Message>> toAddDates = new ArrayList<>();
        for (int i=1; i<messages.size(); i++) {
//            System.out.println("messages day!!! : " + i + ")))(((" + messages.get(i).getSenddate().getDay() );
            //TODO : change deprecated get Day method
            if (messages.get(i-1).getSenddate().getDay() != messages.get(i).getSenddate().getDay()) {
                Message type3message = new Message(messages.get(i).getChatroomId(), messages.get(i).getSenddate(), Message.VIEW_TYPE_MESSAGE_DAY);
                Pair<Integer, Message> pair = new Pair<>(i, type3message);
                toAddDates.add(pair);
            }
        }



        int addedCnt = 0;
        for (Pair<Integer, Message> pair : toAddDates) {
            //TODO why minus?? not plus??
            int index = pair.first - addedCnt++;
            messages.add(index, pair.second);
        }
        return messages;
    }


    static class SentMessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_message_body)
        TextView messageTextView;
        @BindView(R.id.text_message_time)
        TextView timeTextView;
        @BindView(R.id.layout_message_item)
        ViewGroup layout;

        //ChatroomClickListener listener;

        public SentMessageHolder(View itemView, final ChatroomClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //this.listener = listener;
            this.messageTextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //this.listener.onItemClick(v, getAdapterPosition());
                    listener.onMsgClick(getAdapterPosition());
                }
            });

            this.layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onHolderClick();
                }
            });
        }

        void bindMessage(Message message) {
            messageTextView.setText(message.getText());

            if(message.getType()==Message.VIEW_TYPE_MESSAGE_SENT) {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                timeTextView.setText(sdf.format(message.getSenddate()));
            }
        }
    }

    static class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_message_body)
        TextView messageTextView;
        @BindView(R.id.text_message_time)
        TextView timeTextView;
        @BindView(R.id.image_message_profile)
        ImageView profileImageView;
        @BindView(R.id.text_message_name)
        TextView nameTextView;
        @BindView(R.id.layout_message_item)
        ViewGroup layout;

        public ReceivedMessageHolder(View itemView, final ChatroomClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.profileImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onProfileClick(getAdapterPosition());
                }
            });

            this.messageTextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onMsgClick(getAdapterPosition());
                }
            });

            this.layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onHolderClick();
                }
            });
        }

        void bindMessage(Message message) {
            messageTextView.setText(message.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            timeTextView.setText(sdf.format(message.getSenddate()));
            nameTextView.setText(message.getSenderId());
        }
    }

}
