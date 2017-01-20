package com.example.android.salesmonitor.activitiy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.salesmonitor.R;
import com.example.android.salesmonitor.SalesMonitorApp;
import com.example.android.salesmonitor.util.FirebaseNames;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowAllClientsActivity extends AppCompatActivity {

    private static final String TAG = "ShowAllClientsActivity";

    @BindView(R.id.activity_show_all_clients)
    public ListView lstClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_clients);
        ButterKnife.bind(this);

        final Context context = this;

        DatabaseReference clientsRef = FirebaseDatabase.getInstance().getReference(FirebaseNames.getClientsName(SalesMonitorApp.getInstance().getUserEmail()));
        clientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> value = (List<String>)dataSnapshot.getValue();
                if (value == null) {
                    value = new ArrayList<String>();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_dropdown_item_1line, value);
                Log.d(TAG, "Value is: " + value);

                lstClients.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
