package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.content.Context;
import android.support.annotation.NonNull;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018. 3. 28..
 */

public class ChatroomListAdapter extends RecyclerView.Adapter<ChatroomListAdapter.ChatroomHolder> {
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private List<Chatroom> chatrooms;
    private ChatroomListClickListener listener;


    public ChatroomListAdapter(Context context, @NonNull List<Chatroom> chatrooms, ChatroomListClickListener listener){
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chatrooms = chatrooms;
        this.listener = listener;
    }


    @Override
    public ChatroomHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_chatroom, parent, false);
        ChatroomHolder holder = new ChatroomHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ChatroomHolder holder, int position){
        Chatroom chatroom = chatrooms.get(position);
        holder.bindChatroom(chatroom);
    }

    @Override
    public int getItemCount() {
        if(chatrooms == null)
            return 0;
        return chatrooms.size();
    }


    static public class ChatroomHolder extends RecyclerView.ViewHolder{
        private Chatroom chatroom;

        @BindView(R.id.list_item_chatroom_title_text)
        TextView titleTextView;
        @BindView(R.id.list_item_chatroom_thumbnail_img)
        ImageView thumbnailImageView;
        @BindView(R.id.list_item_chatroom_number)
        TextView numberTextView;
        @BindView(R.id.list_item_chatroom_msg_text)
        TextView msgTextView;
        @BindView(R.id.list_item_chatroom_msg_date)
        TextView msgDateTextView;
        @BindView(R.id.layout_chatroom_item)
        ViewGroup layout;


        public ChatroomHolder(View itemView, final ChatroomListClickListener listener){
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onHolderClick(getAdapterPosition());
                }
            });

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

