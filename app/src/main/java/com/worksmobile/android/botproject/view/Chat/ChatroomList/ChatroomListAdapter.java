package com.worksmobile.android.botproject.view.Chat.ChatroomList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Chatroom;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by user on 2018. 3. 28..
 */

public class ChatroomListAdapter extends RecyclerView.Adapter<ChatroomListAdapter.ChatroomHolder>{
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private List<Chatroom> chatrooms;

    public ChatroomListAdapter(List<Chatroom> chatrooms){
        this.chatrooms = chatrooms;
    }

    public ChatroomListAdapter(Context context, List<Chatroom> chatrooms){
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chatrooms = chatrooms;
    }


    @Override
    public ChatroomHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_chatroom, parent, false);
        ChatroomHolder holder = new ChatroomHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChatroomHolder holder, int position){
        Chatroom chatroom = chatrooms.get(position);
        holder.bindChatroom(chatroom);
        holder.itemView.setOnClickListener(new ChatroomListClickListenerImpl(context, chatroom));
    }

    @Override
    public int getItemCount() {
        if(chatrooms == null)
            return 0;
        return chatrooms.size();
    }


    static public class ChatroomHolder extends RecyclerView.ViewHolder{
        private Chatroom chatroom;

        private TextView titleTextView;
        private ImageView thumbnailImageView;
        private TextView numberTextView;
        private TextView msgTextView;
        private TextView msgDateTextView;

        public ChatroomHolder(View itemView){
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.list_item_chatroom_title_text);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.list_item_chatroom_thumbnail_img);
            numberTextView = (TextView) itemView.findViewById(R.id.list_item_chatroom_number);
            msgTextView = (TextView) itemView.findViewById(R.id.list_item_chatroom_msg_text);
            msgDateTextView = (TextView) itemView.findViewById(R.id.list_item_chatroom_msg_date);
        }

        public void bindChatroom(Chatroom chatroom_){
            this.chatroom = chatroom_;
            titleTextView.setText(chatroom.getTitle());
            thumbnailImageView.setImageResource(chatroom.getTumbnail());
            numberTextView.setText("(" + chatroom.getNumber() + ")");
            msgTextView.setText(chatroom.getLatestMsg().getText());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm");
            msgDateTextView.setText(sdf.format(chatroom.getLatestMsg().getSenddate()));
        }
    }
}

