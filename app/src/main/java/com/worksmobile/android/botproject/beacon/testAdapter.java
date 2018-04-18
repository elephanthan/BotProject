package com.worksmobile.android.botproject.beacon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.perples.recosdk.RECOBeacon;
import com.worksmobile.android.botproject.R;

import java.util.ArrayList;

/**
 * Created by user on 2018. 4. 18..
 */

public class testAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<RECOBeacon> data;
    private int layout;

    public testAdapter(Context context, int layout, ArrayList<RECOBeacon> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }

    @Override
    public int getCount(){return data.size();}

    @Override
    public String getItem(int position){return data.get(position).getProximityUuid();}

    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }

        RECOBeacon listviewitem = data.get(position);



        TextView name=(TextView)convertView.findViewById(R.id.textview);
        name.setText(listviewitem.getName());

        return convertView;
    }
}
