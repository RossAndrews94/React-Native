package com.example.android.salesmonitor.activitiy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class AddShopActivity extends AppCompatActivity {

    private static final String TAG = "AddShopActivity";

    @BindView(R.id.txt_shop_email)
    public EditText txtShopEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        ButterKnife.bind(this);
    }

    public void addShop(View view) {
        final Context context = this;
        final String shopEmail = FirebaseNames.getGmailAddress(txtShopEmail.getText().toString());
        DatabaseReference couchesRef = FirebaseDatabase.getInstance().getReference(FirebaseNames.COACHES);
        couchesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<String> value = (List<String>) dataSnapshot.getValue();
                if (value == null) {
                    value = new ArrayList<String>();
                }

                if (value.contains(shopEmail)) {
                    final DatabaseReference shopedRef = FirebaseDatabase.getInstance().getReference(FirebaseNames.getClientsName(shopEmail));
                    shopedRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            List<String> value = (List<String>) dataSnapshot.getValue();
                            Log.d(TAG, "Value is: " + value);
                            if (value ==  null) {
                                value = new ArrayList<String>();
                            }
                            String userEmail = SalesMonitorApp.getInstance().getUserEmail();
                            if (!value.contains(userEmail)) {
                                value.add(userEmail);
                                shopedRef.setValue(value);
                                DatabaseReference userShopRef = FirebaseDatabase.getInstance().getReference(FirebaseNames.getShopName(userEmail));
                                userShopRef.setValue(shopEmail);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
                } else {
                    Toast.makeText(context, "No shop with this email", Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
