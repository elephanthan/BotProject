/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Perples, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.worksmobile.android.botproject.beacon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;

import java.io.File;
import java.util.ArrayList;

public class RecoRangingListAdapter extends BaseAdapter {
    //private ArrayList<RECOBeacon> mRangedBeacons;
    private ArrayList<String> logfileNames;
    private LayoutInflater mLayoutInflater;
    ItemFileClickListener listener;
    Context context;

    public RecoRangingListAdapter(Context context) {
        super();
        //mRangedBeacons = new ArrayList<RECOBeacon>();
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.listener = (ItemFileClickListener) context;

        updateFileList();
    }

    public void updateFileList(){
        logfileNames = new ArrayList<String>();

        String path=context.getFilesDir().getAbsolutePath();
        File dirFile=new File(path);
        File []fileList=dirFile.listFiles();
        for(File tempFile : fileList) {
            if(tempFile.isFile()) {
                String tempPath=tempFile.getParent();
                String tempFileName=tempFile.getName();
//                System.out.println("Path="+tempPath);
//                System.out.println("FileName="+tempFileName);

                logfileNames.add(tempFileName);
            }
        }
    }

    public void clear() {
        logfileNames.clear();
    }

    @Override
    public int getCount() {
        return logfileNames.size();
    }

    @Override
    public Object getItem(int position) {
        return logfileNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView==null) {
            convertView = mLayoutInflater.inflate(R.layout.item_ranging_beacon, parent, false);
            viewHolder.listItemFilename = (TextView) convertView.findViewById(R.id.list_item_logfile);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(viewHolder!=null) {

            final String filename = logfileNames.get(position);

            viewHolder.listItemFilename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.filenameClick(filename);
                }
            });
            viewHolder.listItemFilename.setText(filename);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView listItemFilename;
    }



}
