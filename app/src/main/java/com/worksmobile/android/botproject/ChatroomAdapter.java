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
 * Created by user on 2018. 3. 28..
 */

public class ChatroomAdapter extends RecyclerView.Adapter<ChatroomAdapter.ChatroomHolder>{
    private LayoutInflater inflater;
    private int layout;
    private List<Chatroom> chatrooms;

    public ChatroomAdapter(List<Chatroom> chatrooms){
        this.chatrooms = chatrooms;
    }

    public ChatroomAdapter(Context context, int layout, List<Chatroom> chatrooms){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout=layout;
        this.chatrooms = chatrooms;
    }


    @Override
    public ChatroomHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_item_chatroom, parent, false);
        final ChatroomHolder holder = new ChatroomHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), chatrooms.get(holder.getAdapterPosition()).getTitle() + " 선택됨!", Toast.LENGTH_SHORT).show(); // 이 코드가 핵심이었음...
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ChatroomHolder holder, int position){
        Chatroom chatroom = chatrooms.get(position);
        holder.bindChatroom(chatroom);
    }

    @Override
    public int getItemCount(){
        return chatrooms.size();
    }


    public class ChatroomHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Chatroom mChatroom;

        private TextView mTitleTextView;
        private ImageView mThumbnailImageView;

        public ChatroomHolder(View itemView){
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_chatroom_title_text);
            mThumbnailImageView = (ImageView) itemView.findViewById(R.id.list_item_chatroom_thumbnail_img);
        }

        public void bindChatroom(Chatroom chatroom){
            mChatroom = chatroom;
            mTitleTextView.setText(mChatroom.getTitle());
            mThumbnailImageView.setImageResource(mChatroom.getTumbnail());
        }

        //어댑터 분리하면서 이벤트 삭제
        //이벤트 등록을 홀더에서 어댑터에서 홀더를 생성하면서 등록
        @Override
        public void onClick(View view) {
            //Toast.makeText(view, mChatroom.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}

