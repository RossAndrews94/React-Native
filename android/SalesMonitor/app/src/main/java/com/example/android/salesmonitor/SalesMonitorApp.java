package com.example.android.salesmonitor;

import android.app.Application;
import android.util.Log;

import com.example.android.salesmonitor.manage.DataStoreManager;
import com.example.android.salesmonitor.util.FirebaseNames;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Sergiu on 17.11.2016.
 */

public class SalesMonitorApp extends Application {
    private static final String TAG = "APP";
    private static SalesMonitorApp INSTANCE;
    private DataStoreManager dataStoreManager;

    private String firebaseUserEmail;
    private String fireBaseUserRole;
    private String fireBaseUserShop = "";

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        INSTANCE = (SalesMonitorApp) getApplicationContext();
        dataStoreManager = new DataStoreManager(INSTANCE);
    }

    public DataStoreManager getDataStoreManager() {
        return dataStoreManager;
    }

    public static SalesMonitorApp getInstance() {
        return INSTANCE;
    }

    public String getUserEmail() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            String email = user.getEmail();
//            return email;
//        }
//        return "";
        return firebaseUserEmail;
    }

    public void initFirebaseData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            firebaseUserEmail = user.getEmail();
            dataStoreManager.initFirebase();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(FirebaseNames.getInfoName(firebaseUserEmail));
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    if (value ==  null || value.equals("")) {
                        return;
                    }
                    fireBaseUserRole = value;

                    if (value.equals(FirebaseNames.REG_USER_ROLE)) {
                        DatabaseReference userShopRef = FirebaseDatabase.getInstance().getReference(FirebaseNames.getShopName(firebaseUserEmail));
                        userShopRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                String value = dataSnapshot.getValue(String.class);
                                if (value ==  null || value.equals("")) {
                                    return;
                                }
                                fireBaseUserShop = value;
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w(TAG, "Failed to read value.", error.toException());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
    }

    public String getUserRole() {
        return fireBaseUserRole;
    }

    public String getUserShop() {
        return fireBaseUserShop;
    }
}
