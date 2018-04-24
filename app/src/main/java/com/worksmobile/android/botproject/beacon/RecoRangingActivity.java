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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.worksmobile.android.botproject.R;

/**
 * RECORangingActivity class is to range regions in the foreground.
 *
 * RECORangingActivity 클래스는 foreground 상태에서 ranging을 수행합니다.
 */
public class RecoRangingActivity extends AppCompatActivity implements ItemFileClickListener{

    private RecoRangingListAdapter mRangingListAdapter;
    private ListView mRegionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reco_ranging);
        //TextView textView = (TextView)findViewById(R.id.textView);
        //textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        mRangingListAdapter = new RecoRangingListAdapter(this);
        mRegionListView = (ListView)findViewById(R.id.list_item_filename);
        mRegionListView.setAdapter(mRangingListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRangingListAdapter.updateFileList();
        //mRangingListAdapter.notifyDataSetChanged();
    }


    @Override
    public void filenameClick(String filename) {
        Intent intent = new Intent(this, RecoMonitoringActivity.class);
        intent.putExtra("filename", filename);
        startActivity(intent);
    }
}
