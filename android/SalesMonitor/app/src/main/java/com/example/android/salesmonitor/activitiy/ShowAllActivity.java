package com.example.android.salesmonitor.activitiy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.salesmonitor.R;
import com.example.android.salesmonitor.SalesMonitorApp;
import com.example.android.salesmonitor.domain.SaleActivity;
import com.example.android.salesmonitor.manage.DataStoreManager;
import com.example.android.salesmonitor.util.FirebaseNames;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowAllActivity extends AppCompatActivity {
    private DataStoreManager dataStoreManager;
    @BindView(R.id.lstSales)
    public ListView salesListView;
    private List<SaleActivity> saleActivities;

    ArrayAdapter<SaleActivity> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        setTitle("Sale Activities");
        ButterKnife.bind(this);

//        dataStoreManager = DataStoreManager.getInstance();
        dataStoreManager = SalesMonitorApp.getInstance().getDataStoreManager();
//        salesListView = (ListView) findViewById(R.id.lstSales);

//        List<String> saleActivities = new ArrayList<>();
//        for (SaleActivity saleActivity: dataStoreManager.getSaleActivities()) {
//            saleActivities.add(saleActivity.toString());
//        }

        saleActivities = dataStoreManager.getSaleActivities();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, saleActivities);

        salesListView.setAdapter(adapter);

        final Context context = this;

        salesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SaleActivity saleActivity = (SaleActivity) salesListView.getItemAtPosition(position);

                Intent intent = new Intent(context, SaveSaleActivity.class);

                intent.putExtra("title", "Update Sale Activity");
                intent.putExtra("saleActivityId", saleActivity.getId());

//                startActivity(intent);
                startActivityForResult(intent, 1);

            }
        });

        DatabaseReference userActivitiesRef = FirebaseDatabase.getInstance().getReference(FirebaseNames.getActivitiesName(SalesMonitorApp.getInstance().getUserEmail()));
        userActivitiesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                saleActivities = dataStoreManager.getSaleActivities();
                adapter = new ArrayAdapter<SaleActivity>(context,
                        android.R.layout.simple_dropdown_item_1line, saleActivities);

                salesListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("A", "onActivityResult: ");
        saleActivities.clear();
        saleActivities.addAll(dataStoreManager.getSaleActivities());
        adapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }

}
