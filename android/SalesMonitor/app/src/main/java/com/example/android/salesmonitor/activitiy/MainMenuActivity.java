package com.example.android.salesmonitor.activitiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.salesmonitor.R;
import com.example.android.salesmonitor.SalesMonitorApp;
import com.example.android.salesmonitor.util.FirebaseNames;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMenuActivity extends AppCompatActivity {

    @BindView(R.id.display_usr_email)
    public TextView tvEmail;

    @BindView(R.id.display_role)
    public TextView tvRole;

    @BindView(R.id.tv_shop_name)
    public TextView tvShopName;

    @BindView(R.id.btn_add_shop)
    public Button btnAddShop;

    @BindView(R.id.btn_view_clients)
    public Button btnViewClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
//        DataStoreManager.getInstance();
        ButterKnife.bind(this);
        tvRole.setText(FirebaseNames.getUserRole(SalesMonitorApp.getInstance().getUserRole()));
        initUIDependingOnRole();
        tvEmail.setText("Signed in as: " + SalesMonitorApp.getInstance().getUserEmail());
        makeAnimation();
    }

    private void makeAnimation() {
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(20000);
        tvEmail.startAnimation(animation);
    }

    private void initUIDependingOnRole() {
        String role = SalesMonitorApp.getInstance().getUserRole();
        if (role.equals(FirebaseNames.COACH_ROLE)) {
            btnAddShop.setVisibility(View.INVISIBLE);
            tvShopName.setVisibility(View.INVISIBLE);
            btnViewClients.setVisibility(View.VISIBLE);
        } else {
            btnViewClients.setVisibility(View.INVISIBLE);
            showHideShopName();
            DatabaseReference shopName = FirebaseDatabase.getInstance().getReference(FirebaseNames.getShopName(SalesMonitorApp.getInstance().getUserEmail()));
            shopName.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    showHideShopName();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
    }

    private void showHideShopName() {
        String shopName = SalesMonitorApp.getInstance().getUserShop();
        if (shopName.equals("")) {
            btnAddShop.setVisibility(View.VISIBLE);
            tvShopName.setVisibility(View.INVISIBLE);
        } else {
            btnAddShop.setVisibility(View.INVISIBLE);
            tvShopName.setVisibility(View.VISIBLE);
            tvShopName.setText("Shop: " + shopName);
        }
    }

    public void onBtnAddSaleActivityClick(View view) {
        Intent intent = new Intent(this, SaveSaleActivity.class);
        startActivity(intent);
    }

    public void onBtnShowAllSaleActivityClick(View view) {
        Intent intent = new Intent(this, ShowAllActivity.class);
        startActivity(intent);
    }

    public void onBtnAddshopClick(View view) {
        Intent intent = new Intent(this, AddShopActivity.class);
        startActivity(intent);
    }

    public void onBtnViewClientsClick(View view) {
        Intent intent = new Intent(this, ShowAllClientsActivity.class);
        startActivity(intent);
    }
}
