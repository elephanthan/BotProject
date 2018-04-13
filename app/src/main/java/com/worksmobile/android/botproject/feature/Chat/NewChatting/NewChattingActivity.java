package com.worksmobile.android.botproject.feature.Chat.NewChatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class NewChattingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newchatting);
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            FileInputStream fis = openFileInput("mybeacon.txt");//파일명
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fis));
            String str = buffer.readLine();                 // 파일에서 한줄을 읽어옴
            String str2 = str;
            while (str != null) {
                data.append(str + "\n");
                str = buffer.readLine();
                str2 += str;
            }
            textView.setText(str2);
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
