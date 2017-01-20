package com.example.rossandrews.testa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.rossandrews.testa.activity.FireBaseAuthActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, FireBaseAuthActivity.class);
        startActivity(intent);

        finish();
    }
}

/*
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public void onButtonClick(View v){

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    public void onButtonClick(View v){
        Log.d("Main", ""+v.getId());
        if(v.getId() == R.id.LOGIN_Button){
            Log.d("MAIN", "Hello");
            EditText user = (EditText) findViewById(R.id.TF_UserName);
            EditText pass = (EditText) findViewById(R.id.TF_Password);

            Intent intent = new Intent(MainActivity.this, MenuAfterLogin.class);

            // send username and password to menuAfterLogin
            intent.putExtra("Username", user.getText().toString());
            intent.putExtra("Password", pass.getText().toString());
            startActivityForResult(intent, 0);

        }
    }
}
*/
/*

*/
/*

*/

