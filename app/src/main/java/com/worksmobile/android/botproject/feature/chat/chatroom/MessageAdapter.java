package com.worksmobile.android.botproject.feature.chat.chatroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018. 3. 30..
 */

public class MessageAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private List<Message> messages = new ArrayList<>();
    private List<User> users;

    ChatroomClickListener listner;


    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    public MessageAdapter(Context context, @NonNull List<Message> messages) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messages = messages;
        this.users = UserLab.get().getUsers();
    }

    public MessageAdapter(Context context, @NonNull List<Message> messages, ChatroomClickListener listener) {
        this.context = context;
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
                view = inflater.inflate(R.layout.item_message_received, parent, false);
                holder = new ReceivedMessageHolder(view, listner);
        }
        return holder;
    }

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
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = messages.get(position);

        //TODO: sendbird의 ID값과 Message의 USERID값을 비교해서 VIEWTYPE 결정해주기 (지금은 짝,홀 1,2 리턴)

        return msg.getType();
    }

    static class SentMessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_message_body)
        TextView messageTextView;
        @BindView(R.id.text_message_time)
        TextView timeTextView;

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
        }

        void bindMessage(Message message) {
            messageTextView.setText(message.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            timeTextView.setText(sdf.format(message.getSenddate()));
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

                }

            });
        }

        void bindMessage(Message message) {
            messageTextView.setText(message.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            timeTextView.setText(sdf.format(message.getSenddate()));
        }
    }

}
