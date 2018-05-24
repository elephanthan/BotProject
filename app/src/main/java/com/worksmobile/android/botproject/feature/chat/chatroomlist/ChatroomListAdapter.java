package com.worksmobile.android.botproject.feature.chat.chatroomlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Chatroom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.worksmobile.android.botproject.util.ViewUtil.getResizedTextViewText;

/**
 * Created by user on 2018. 3. 28..
 */

public class ChatroomListAdapter extends RecyclerView.Adapter<ChatroomListAdapter.ChatroomHolder>
                                    implements ChatroomListContract.AdapterView, ChatroomListDataModel {

    private static Context context;
    private LayoutInflater inflater;
    private List<Chatroom> chatrooms = new ArrayList<>();
    private ChatroomListClickListener listener;

    public ChatroomListAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ChatroomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_chatroom, parent, false);
        return new ChatroomHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(final ChatroomHolder holder, int position) {
        Chatroom chatroom = getChatroom(position);
        holder.bindChatroom(chatroom);
    }

    @Override
    public int getItemCount() {
        return getSize();
    }


    public void setList(List<Chatroom> chatrooms) {
        this.chatrooms = chatrooms;
    }

    @Override
    public void refresh(List<Chatroom> chatrooms) {
        setList(chatrooms);
        notifyDataSetChanged();
    }

    @Override
    public void add(Chatroom chatroom) {
        chatrooms.add(chatroom);
    }

    @Override
    public Chatroom remove(int position) {
        return chatrooms.remove(position);
    }

    @Override
    public Chatroom getChatroom(int position) {
        return chatrooms.get(position);
    }

    @Override
    public int getSize() {
        return chatrooms.size();
    }

    @Override
    public List<Chatroom> getAllChatrooms() {
        return this.chatrooms;
    }


    static public class ChatroomHolder extends RecyclerView.ViewHolder {
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


        public ChatroomHolder(View itemView, final ChatroomListClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.layout.setOnClickListener(v -> listener.onHolderClick(getAdapterPosition()));
        }

        public void bindChatroom(Chatroom chatroom_) {
            this.chatroom = chatroom_;

            String titleText = getResizedTextViewText(context,titleTextView, chatroom.getViewTitle());
            titleTextView.setText(titleText);

            int chatroomNumber = chatroom.getNumber();
            if(chatroomNumber > 2) {
                numberTextView.setVisibility(View.VISIBLE);
                numberTextView.setText("(" + chatroomNumber + ")");
            } else {
                numberTextView.setVisibility(View.INVISIBLE);
            }

            if (chatroom.getLastMessageContent() != null) {
                msgTextView.setVisibility(View.VISIBLE);
                msgTextView.setText(chatroom.getLastMessageContent());
            } else {
                msgTextView.setVisibility(View.INVISIBLE);
            }

            int defaultImage;
            if (chatroom.getChatroomType() == Chatroom.CHATROOM_TYPE_BOT) {
                defaultImage = R.drawable.ic_icon_bot;
            } else {
                if (chatroom.getNumber() <= 2) {
                    defaultImage = R.drawable.ic_icon_man;
                } else {
                    defaultImage = R.drawable.ic_icon_men;
                }
            }
            Glide.with(context).load(chatroom.getProfile()).placeholder(defaultImage).error(defaultImage).into(thumbnailImageView);

            SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String displayformat = "MM-dd HH:mm";
            SimpleDateFormat destFormat = new SimpleDateFormat(displayformat, Locale.KOREA);
            try {
                if (chatroom.getLastMessageTime() != null) {
                    Date myDate = pattern.parse(chatroom.getLastMessageTime());
                    msgDateTextView.setText(destFormat.format(myDate));
                    msgDateTextView.setVisibility(View.VISIBLE);
                } else {
                    msgDateTextView.setVisibility(View.INVISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnRecyclerItemClickListener(ChatroomListClickListener onRecyclerItemClickListener) {
        this.listener = onRecyclerItemClickListener;
    }

}

