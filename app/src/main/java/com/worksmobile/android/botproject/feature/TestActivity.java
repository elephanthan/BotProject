package com.worksmobile.android.botproject.feature;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.data.DBContactHelper;
import com.worksmobile.android.botproject.model.Contact;

import java.util.List;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        DBContactHelper db = new DBContactHelper(this);

        /**
         * CRUD Operations
         * */
        // 샘플데이타 넣기
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // 집어넣은 데이타 다시 읽어들이기
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            Log.d("Name: ", log);
        }

    }

}

