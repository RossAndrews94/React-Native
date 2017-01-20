package com.example.rossandrews.testa;

import android.app.Application;
import android.util.Log;

import com.example.rossandrews.testa.manage.DataStoreManager;
import com.example.rossandrews.testa.utils.FirebaseNames;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ross Andrews on 1/19/2017.
 */

public class ClientsApp extends Application {
    private static final String TAG = "APP";
    private static ClientsApp INSTANCE;
    private DataStoreManager dataStoreManager;

    private String firebaseUserEmail;
    private String firebaseUserRole;
    private String fireBaseUserClient = "";

    public static ClientsApp getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        INSTANCE = (ClientsApp) getApplicationContext();
        dataStoreManager = new DataStoreManager(INSTANCE);
    }

    public DataStoreManager getDataSStoreManager()
    {
        return dataStoreManager;
    }

    public String getUserEmail(){
        return firebaseUserEmail;
    }

    public void initFirebaseData(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            firebaseUserEmail = user.getEmail();
            dataStoreManager.initFirebase();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(FirebaseNames.getInfoName(firebaseUserEmail));
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    if (value ==  null || value.equals("")) {
                        return;
                    }
                    firebaseUserRole = value;

                    if(value.equals(FirebaseNames.REG_USER_ROLE)){
                        DatabaseReference userClientRef = FirebaseDatabase.getInstance().getReference(FirebaseNames.getClientName(firebaseUserEmail));
                        userClientRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String value = dataSnapshot.getValue(String.class);
                                if (value ==  null || value.equals("")) {
                                    return;
                                }
                                fireBaseUserClient = value;
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w(TAG, "Failed to read value.", databaseError.toException());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                }
            });

        }
    }

    public String getUserRole(){
        return firebaseUserRole;
    }

    public String getUserClient(){
        return fireBaseUserClient;
    }
}
