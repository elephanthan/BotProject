package com.worksmobile.android.botproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 2018. 3. 28..
 */

public class ChatroomAdapter extends RecyclerView.Adapter<ChatroomAdapter.ChatroomHolder>{
    private LayoutInflater inflater;
    private int layout;
    private List<Chatroom> mChatrooms;



    public ChatroomAdapter(List<Chatroom> chatrooms){
        this.mChatrooms = chatrooms;
    }

    public ChatroomAdapter(Context context, int layout, List<Chatroom> chatrooms){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout=layout;
        this.mChatrooms = chatrooms;
    }


    @Override
    public ChatroomHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_item_chatroom, parent, false);

        return new ChatroomHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatroomHolder holder, int position1){
        Chatroom chatroom = mChatrooms.get(position1);
        holder.bindChatroom(chatroom);

    }

    @Override
    public int getItemCount(){
        return mChatrooms.size();
    }

    public class ChatroomHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Chatroom mChatroom;

        private TextView mTitleTextView;
        private ImageView mThumbnailImageView;

        public ChatroomHolder(View itemView){
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_chatroom_title_text);
            mThumbnailImageView = (ImageView) itemView.findViewById(R.id.list_item_chatroom_thumbnail_img);
            //itemView.setOnClickListener(this);
        }

        public void bindChatroom(Chatroom chatroom){
            mChatroom = chatroom;
            mTitleTextView.setText(mChatroom.getTitle());
            mThumbnailImageView.setImageResource(mChatroom.getTumbnail());
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view, mChatroom.getTitle(), Toast.LENGTH_SHORT).show();

        }
    }
}

