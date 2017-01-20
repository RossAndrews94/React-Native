package com.example.rossandrews.testa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rossandrews.testa.ClientsApp;
import com.example.rossandrews.testa.MenuAfterLogin;
import com.example.rossandrews.testa.R;
import com.example.rossandrews.testa.utils.FirebaseNames;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ross Andrews on 1/18/2017.
 */

public class FireBaseAuthActivity extends AppCompatActivity {

    private static final String TAG = "FIREBASE";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public EditText text_Username;// = (EditText) findViewById(R.id.text_Username);
    public EditText text_Password;// = (EditText) findViewById(R.id.text_Password);

    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_firebase_auth);
        text_Username = (EditText) findViewById(R.id.text_Username);
        text_Password = (EditText) findViewById(R.id.text_Password);

        initFirebase();
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
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

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createAccount(View view) {
        String email = text_Username.getText().toString();
        String password = text_Password.getText().toString();
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.contains("@")) {
            email += "@gmail.com";
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "createUserWithEmail:onComplete:", task.getException());
                            Toast.makeText(FireBaseAuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // GO TO APP
//                        SportsMonitorApp.getInstance().initFirebaseData();


                        confirmStatus();
                        // ...
                    }
                });
    }

    public void signInExistingUser(View view) {
        String email = text_Username.getText().toString();
        String password = text_Password.getText().toString();
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT);
            return;
        }
        email = FirebaseNames.getGmailAddress(email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(FireBaseAuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                                Toast.makeText(getApplicationContext(), "It Works!", Toast.LENGTH_SHORT).show();
                                return;
                        }

                        ClientsApp.getInstance().initFirebaseData();
                        confirmStatus();

                        // ...
                    }
                });
    }

    private void confirmStatus() {
        final Context context = this;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
//            String name = user.getDisplayName();
            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
//            String uid = user.getUid();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(FirebaseNames.getInfoName(email));
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Value is: " + value);
                    if (value ==  null || value.equals("")) {
//                        hideUi();
                        return;
                    }
/*
                    if(true) {
                        Toast.makeText(getApplicationContext(), "It Works!", Toast.LENGTH_SHORT).show();
                    }
*/

                    Intent intent = new Intent(context, ClientListActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });


        }
    }


}
