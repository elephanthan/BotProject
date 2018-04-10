package com.worksmobile.android.botproject.feature.Chat.Chatroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.feature.dialog.UserinfoDialogFragment;
import com.worksmobile.android.botproject.model.Message;
import com.worksmobile.android.botproject.model.User;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by user on 2018. 3. 30..
 */

public class MessageAdapter extends RecyclerView.Adapter implements ChatroomClickListener{
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private List<Message> messages;
    private List<User> users;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    public MessageAdapter(Context context, @NonNull List<Message> messages) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messages = messages;
        this.users = UserLab.get().getUsers();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        final RecyclerView.ViewHolder holder;

        switch (viewType){
            case VIEW_TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.item_message_sent, parent, false);
                holder = new SentMessageHolder(view);
                ((SentMessageHolder) holder).messageTextView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        onItemClick(v, holder.getAdapterPosition());
                    }
                });
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                view = inflater.inflate(R.layout.item_message_received, parent, false);
                holder = new ReceivedMessageHolder(view);

                ((ReceivedMessageHolder) holder).profileImageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        onItemClick(v, holder.getAdapterPosition());
                    }
                });

                ((ReceivedMessageHolder) holder).messageTextView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        onItemClick(v, holder.getAdapterPosition());
                    }
                });
                break;
            default:
                view = inflater.inflate(R.layout.item_message_received, parent, false);
                holder = new ReceivedMessageHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message msg = messages.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bindMessage(msg);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bindMessage(msg);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(messages == null)
            return 0;
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = messages.get(position);

        //TODO sendbird의 ID값과 Message의 USERID값을 비교해서 VIEWTYPE 결정해주기 (지금은 짝,홀 1,2 리턴)
        //if(msg.getSenderId().equals(SendBird.getCurrentUser().getUserId()))
        //return VIEW_TYPE_MESSAGE_SENT;
        //else
        //return VIEW_TYPE_MESSAGE_RECEIVED;

        return msg.getType();
    }

    static private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageTextView, timeTextView;

        public SentMessageHolder(View itemView) {
            super(itemView);
            messageTextView = (TextView) itemView.findViewById(R.id.text_message_body);
            timeTextView = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bindMessage(Message message) {
            messageTextView.setText(message.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            timeTextView.setText(sdf.format(message.getSenddate()));
        }
    }

    static private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageTextView, timeTextView, nameTextView;
        ImageView profileImageView;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageTextView = (TextView) itemView.findViewById(R.id.text_message_body);
            timeTextView = (TextView) itemView.findViewById(R.id.text_message_time);
            profileImageView = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bindMessage(Message message) {
            messageTextView.setText(message.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            timeTextView.setText(sdf.format(message.getSenddate()));
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.image_message_profile :
                //TODO (FragmentActivty)를 사용하지 않고 처리
                FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
                UserinfoDialogFragment dialogFragment = UserinfoDialogFragment.newInstance(messages.get(position).getSenderId());

                dialogFragment.show(fm,"fragment_dialog_test");
                //dialogFragment.show("fragment_dialog_test");
                break;
            case R.id.text_message_body :
                Toast.makeText(context, messages.get(position).getText(), Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }
}
