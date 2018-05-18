package com.worksmobile.android.botproject.feature.chat.chatroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 5. 7..
 */

public class DropdownMenuAdapter extends BaseAdapter {
    private LayoutInflater inflator;
    private List<DropDownMenu> dropDownMenus = new ArrayList<DropDownMenu>();

    public DropdownMenuAdapter(Context context, List<DropDownMenu> dropDownMenuList) {
        this.dropDownMenus = dropDownMenuList;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dropDownMenus.size();
    }

    @Override
    public Object getItem(int position) {
        return dropDownMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inflator.inflate(R.layout.row_dropdown, null);
        ImageView iconImageView = convertView.findViewById(R.id.dd_imageview);
        TextView nameTextView = convertView.findViewById(R.id.dd_textview);

        iconImageView.setImageResource(dropDownMenus.get(position).getIconImg());
        nameTextView.setText(dropDownMenus.get(position).getName());

        return convertView;
    }
}
