package com.worksmobile.android.botproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by user on 2018. 3. 30..
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder>{
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private List<Message> messages;

    public MessageAdapter(List<Message> messages){
        this.messages = messages;
    }

    public MessageAdapter(Context context, List<Message> messages){
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messages = messages;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_message, parent, false);
        final MessageHolder holder = new MessageHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(v.getContext(), messages.get(holder.getAdapterPosition()).getText(), Toast.LENGTH_SHORT);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MessageHolder holder, int position) {
        Message msg = messages.get(position);
        holder.bindMessage(msg);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageHolder extends  RecyclerView.ViewHolder {
        private Message msg;

        private ImageView userImageView;
        private TextView messageTextView;
        private TextView messageDateView;

        public MessageHolder(View itemView){
            super(itemView);
            messageTextView = (TextView) itemView.findViewById(R.id.list_item_msg_text);
        }

        public void bindMessage(Message message){
            this.msg = message;
            messageTextView.setText(msg.getText());
        }
    }

}
