package com.example.android.salesmonitor.activitiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.salesmonitor.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, FirebaseAuthActivity.class);
        startActivity(intent);

        //finish this activity
        finish();
    }
}
