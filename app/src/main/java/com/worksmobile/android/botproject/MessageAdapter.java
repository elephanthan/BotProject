package com.worksmobile.android.botproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by user on 2018. 3. 30..
 */

public class MessageAdapter extends RecyclerView.Adapter{
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private List<Message> messages;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public MessageAdapter(List<Message> messages){
        this.messages = messages;
    }

    public MessageAdapter(Context context, List<Message> messages){
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;
        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            view = inflater.inflate(R.layout.item_message_sent, parent, false);
            holder = new SentMessageHolder(view);
        }else{
            view = inflater.inflate(R.layout.item_message_received, parent, false);
            holder = new ReceivedMessageHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message msg = messages.get(position);
        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT :
                ((SentMessageHolder)holder).bindMessage(msg);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED :
                ((ReceivedMessageHolder)holder).bindMessage(msg);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position){
        Message msg = messages.get(position);

        //TODO sendbird의 ID값과 Message의 USERID값을 비교해서 VIEWTYPE 결정해주기 (지금은 짝,홀 1,2 리턴)
        //if(msg.getSenderId().equals(SendBird.getCurrentUser().getUserId()))
        //return VIEW_TYPE_MESSAGE_SENT;
        //else
        //return VIEW_TYPE_MESSAGE_RECEIVED;

        return msg.getType();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView messageTextView, timeTextView;

        public SentMessageHolder(View itemView){
            super(itemView);
            messageTextView = (TextView) itemView.findViewById(R.id.text_message_body);
            timeTextView = (TextView) itemView.findViewById(R.id.text_message_time);

            //itemView.setOnClickListener(this);
            messageTextView.setOnClickListener(this);

        }

        void bindMessage(Message message){
            messageTextView.setText(message.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm");
            timeTextView.setText(sdf.format(message.getSenddate()));

        }

        @Override
        public void onClick(View v){
            Toast.makeText(context, messages.get(getAdapterPosition()).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView messageTextView, timeTextView, nameTextView;
        ImageView profileImageView;

        public ReceivedMessageHolder(View itemView){
            super(itemView);
            messageTextView = (TextView) itemView.findViewById(R.id.text_message_body);
            timeTextView = (TextView) itemView.findViewById(R.id.text_message_time);
            profileImageView = (ImageView) itemView.findViewById(R.id.image_message_profile);
            //TODO Set Username, Userimage

            profileImageView.setOnClickListener(this);
        }

        void bindMessage(Message message){
            messageTextView.setText(message.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm");
            timeTextView.setText(sdf.format(message.getSenddate()));
        }

        @Override
        public void onClick(View v){

            Toast.makeText(context, messages.get(getAdapterPosition()).getText(), Toast.LENGTH_SHORT).show();
        }
    }

}
