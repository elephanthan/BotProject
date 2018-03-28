package com.worksmobile.android.botproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ChatroomListActivity extends AppCompatActivity {

    private RecyclerView mChatroomRecyclerView;
    private ChatroomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        mChatroomRecyclerView = (RecyclerView) findViewById(R.id.chat_room_recycler_view);
        mChatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();
    }

    private class ChatroomHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Chatroom mChatroom;

        private TextView mTitleTextView;
        private ImageView mThumbnailImageView;

        public ChatroomHolder(View itemView){
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_chatroom_title_text);
            mThumbnailImageView = (ImageView) itemView.findViewById(R.id.list_item_chatroom_thumbnail_img);
            itemView.setOnClickListener(this);
        }

        public void bindChatroom(Chatroom chatroom){
            mChatroom = chatroom;
            mTitleTextView.setText(mChatroom.getTitle());
            mThumbnailImageView.setImageResource(mChatroom.getTumbnail());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(ChatroomListActivity.this, mChatroom.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    private class ChatroomAdapter extends RecyclerView.Adapter<ChatroomHolder>{
        private List<Chatroom> mChatrooms;

        public ChatroomAdapter(List<Chatroom> chatrooms){
            mChatrooms = chatrooms;
        }

        @Override
        public ChatroomHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view = layoutInflater.inflate(R.layout.list_item_chatroom, parent, false);
            return new ChatroomHolder(view);
        }

        @Override
        public void onBindViewHolder(ChatroomHolder holder, int position){
            Chatroom chatroom = mChatrooms.get(position);
            holder.bindChatroom(chatroom);
        }

        @Override
        public int getItemCount(){
            return mChatrooms.size();
        }

    }

    private void updateUI(){
        ChatroomLab chatroomLab = ChatroomLab.get(getApplicationContext());
        List<Chatroom> chatrooms = chatroomLab.getChatrooms();

        mAdapter = new ChatroomAdapter(chatrooms);
        mChatroomRecyclerView.setAdapter(mAdapter);
    }

}
