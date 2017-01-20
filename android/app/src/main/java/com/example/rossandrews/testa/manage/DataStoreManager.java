package com.example.rossandrews.testa.manage;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.rossandrews.testa.ClientsApp;
import com.example.rossandrews.testa.activity.FireBaseAuthActivity;
import com.example.rossandrews.testa.database.Client;
import com.example.rossandrews.testa.database.ClientRealm;
import com.example.rossandrews.testa.utils.FirebaseNames;
import com.example.rossandrews.testa.utils.HashMapToObjectConverter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;

/**
 * Created by Ross Andrews on 1/19/2017.
 */

public final class DataStoreManager {
    private static final String TAG = "DataStoreManager";
    private static DataStoreManager INSTANCE;

    private Set<String> clientNames;
    private List<ClientRealm> clients;

    private int id;

    private Realm realm;
    private Context context;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;
    private DatabaseReference userClientsRef;

    public DataStoreManager(Context context){
        this.context = context;
        clientNames = new HashSet<>();
    }

    public void initFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        String userEmail = ClientsApp.getInstance().getUserEmail();
        if(!userEmail.equals("")) {
            userRef = firebaseDatabase.getReference(FirebaseNames.getInfoName(userEmail));
            loadData();
        }
    }

    private void loadData(){
        userClientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, Object>> maps = (List<HashMap<String, Object>>) dataSnapshot.getValue();
                if (maps == null) {
                    maps = new ArrayList<HashMap<String, Object>>();
                }
                clients = HashMapToObjectConverter.convertHashMapListToObjectList(maps);
                for (ClientRealm clientRealm : clients) {
                    if (clientRealm.getId() > id) {
                        id = clientRealm.getId();
                    }
                    clientNames.add(clientRealm.getUsername());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });


    }
}
