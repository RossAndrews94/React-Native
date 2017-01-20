package com.example.rossandrews.testa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import com.example.rossandrews.testa.R;

/**
 * Created by Ross Andrews on 1/19/2017.
 */

public class MainMenuActivity extends AppCompatActivity {

    public Button buttonGoToClients;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    private void makeAnimation(){
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(15000);
        buttonGoToClients.setAnimation(animation);
    }

    public void goToClients(View v){
        /*
        Intent intent = new Intent(this, ClientListActivity.class);
        startActivity(intent);
        */
    }
}
