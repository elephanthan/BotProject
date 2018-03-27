package com.worksmobile.android.botproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2018. 3. 27..
 */

public class ChatroomListAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<Chatroom> chatrooms;
    private int layout;

    public ChatroomListAdapter(Context context, int layout, ArrayList<Chatroom> chatrooms){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chatrooms = chatrooms;
        this.layout=layout;
    }

    @Override
    public int getCount(){return chatrooms.size();}

    @Override
    public String getItem(int position){return chatrooms.get(position).getTitle();}

    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }

        Chatroom chatroom = chatrooms.get(position);
        ImageView thumnail = (ImageView)convertView.findViewById(R.id.list_item_chatroom_thumbnail_img);
        thumnail.setImageResource(chatroom.getTumbnail());
        TextView title = (TextView)convertView.findViewById(R.id.list_item_chatroom_title_text);
        title.setText(chatroom.getTitle());

        return convertView;
    }
}
