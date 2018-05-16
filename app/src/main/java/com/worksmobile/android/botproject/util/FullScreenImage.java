package com.worksmobile.android.botproject.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.worksmobile.android.botproject.R;

public class FullScreenImage extends Activity {

    final public static String EXTRA_IMAGE_URL = "imageUrl";
    final public static String EXTRA_POSITION = "position";

    int position;

    public static Intent newIntent(Context context, String imageUrl, int position){
        Intent intent = new Intent(context, FullScreenImage.class);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        Bundle extras = getIntent().getExtras();
        String imageUrl = extras.getString(EXTRA_IMAGE_URL);
        position = extras.getInt(EXTRA_POSITION);

        ImageView imageViewFullScreen = (ImageView) findViewById(R.id.image_fullscreen);;
        Button btnClose = (Button) findViewById(R.id.button_close);;

        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FullScreenImage.this.finish();
            }
        });

        Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_icon_noshow).error(R.drawable.ic_icon_noshow).into(imageViewFullScreen);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_POSITION, position);
        setResult(RESULT_OK, intent);

        super.finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}