package com.example.android.salesmonitor.manage;

import android.content.Context;
import android.util.Log;

import com.example.android.salesmonitor.SalesMonitorApp;
import com.example.android.salesmonitor.domain.SaleActivity;
import com.example.android.salesmonitor.domain.SaleActivityTime;
import com.example.android.salesmonitor.realm.RealmSaleActivity;
import com.example.android.salesmonitor.util.FirebaseNames;
import com.example.android.salesmonitor.util.HashMapToObjectConverter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import io.realm.Realm;

/**
 * Created by Sergiu on 22.10.2016.
 */
public final class DataStoreManager {
    private static final String TAG = "DataStoreManager";
    private static DataStoreManager INSTANCE;

    private Set<String> saleNames;
    private List<RealmSaleActivity> saleActivities;

    private int id;

    private Realm realm;
    private Context context;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;
    private DatabaseReference userActivitiesRef;

    public DataStoreManager(Context context) {
        this.context = context;
        saleNames = new HashSet<>();
//        realm = Realm.getInstance(context);
//        loadData();
    }

//    public static DataStoreManager getInstance() {
//        if (INSTANCE == null) {
//            INSTANCE = new DataStoreManager();
//        }
//        return INSTANCE;
//    }

    public void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        String userEmail = SalesMonitorApp.getInstance().getUserEmail();
        if (!userEmail.equals("")) {
            userRef = firebaseDatabase.getReference(FirebaseNames.getInfoName(userEmail));
            userActivitiesRef = firebaseDatabase.getReference(FirebaseNames.getActivitiesName(userEmail));
            loadData();
        }
    }

    private void loadData() {
        userActivitiesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<HashMap<String, Object>> maps = (List<HashMap<String, Object>>)dataSnapshot.getValue();
                if (maps == null) {
                    maps = new ArrayList<HashMap<String, Object>>();
                }
                saleActivities = HashMapToObjectConverter.convertHashMapListToObjectList(maps);
                for (RealmSaleActivity realmSaleActivity : saleActivities) {
                    if (realmSaleActivity.getId() > id) {
                        id = realmSaleActivity.getId();
                    }
                    saleNames.add(realmSaleActivity.getSaleName());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
//        realm = Realm.getInstance(context);
//        realm.beginTransaction();
//        try {
//            id = realm.where(RealmSaleActivity.class).max("id").intValue();
//            for (RealmSaleActivity realmSaleActivity : realm.where(RealmSaleActivity.class).findAll()) {
//                saleNames.add(realmSaleActivity.getSaleName());
//            }
//        } finally {
//            realm.commitTransaction();
//            realm.close();
//        }
    }

    public Set<String> getSaleNames() {
        return saleNames;
    }


    public void saveSaleActivity(SaleActivity saleActivity) {
        if (!saleNames.contains(saleActivity.getSaleName())) {
            saleNames.add(saleActivity.getSaleName());
        }
        if (saleActivity.getId() <= 0) {
            saleActivity.setId(generateNextId());
            saleActivities.add(new RealmSaleActivity(saleActivity));
            userActivitiesRef.setValue(saleActivities);
//            realm = Realm.getInstance(context);
//            realm.beginTransaction();
//            try {
//                realm.copyToRealm(new RealmSaleActivity(saleActivity));
//            } finally {
//                realm.commitTransaction();
//                realm.close();
//            }
        } else {
            for (Iterator<RealmSaleActivity> it = saleActivities.iterator(); it.hasNext();) {
                if (it.next().getId() == saleActivity.getId()) {
                    it.remove();
                    break;
                }
            }
            saleActivities.add(new RealmSaleActivity(saleActivity));
            userActivitiesRef.setValue(saleActivities);
//            realm = Realm.getInstance(context);
//            realm.beginTransaction();
//            try {
//                realm.where(RealmSaleActivity.class).equalTo("id", saleActivity.getId()).findAll().clear();
//                realm.copyToRealm(new RealmSaleActivity(saleActivity));
//            } finally {
//                realm.commitTransaction();
//                realm.close();
//            }
        }
    }

    public List<SaleActivity> getSaleActivities() {
        List<SaleActivity> realSaleActivities = new ArrayList<>();
        for (RealmSaleActivity realmSaleActivity : saleActivities) {
            realSaleActivities.add(new SaleActivity(realmSaleActivity));
        }
        return realSaleActivities;
//        realm = Realm.getInstance(context);
//        realm.beginTransaction();
//        try {
//            List<SaleActivity> saleActivities = new ArrayList<>();
//            for (RealmSaleActivity realmSaleActivity : realm.where(RealmSaleActivity.class).findAll()) {
//                saleActivities.add(new SaleActivity(realmSaleActivity));
//            }
//            return saleActivities;
////            return realm.where(RealmSaleActivity.class).findAll()
////                    .stream()
////                    .map(realmSaleActivity -> new SaleActivity(realmSaleActivity))
////                    .collect(Collectors.toList());
//        } finally {
//            realm.commitTransaction();
//            realm.close();
//        }
    }

    private int generateNextId() {
        return ++id;
    }

    public SaleActivity getSaleActivityById(int id) {
        for (RealmSaleActivity realmSaleActivity : saleActivities) {
            if (realmSaleActivity.getId() == id) {
                return new SaleActivity(realmSaleActivity);
            }
        }
        return null;
//        realm = Realm.getInstance(context);
//        realm.beginTransaction();
//        try {
//            RealmSaleActivity realmSaleActivity = realm.where(RealmSaleActivity.class).equalTo("id", id).findFirst();
//            if (realmSaleActivity == null) {
//                return null;
//            }
//            return new SaleActivity(realmSaleActivity);
//        } finally {
//            realm.commitTransaction();
//            realm.close();
//        }
    }

    public boolean deleteSaleActivityById(int id) {
        boolean found  = false;
        for (Iterator<RealmSaleActivity> it = saleActivities.iterator(); it.hasNext();) {
            if (it.next().getId() == id) {
                it.remove();
                found = true;
                break;
            }
        }
        userActivitiesRef.setValue(saleActivities);
        return found;
//        realm = Realm.getInstance(context);
//        realm.beginTransaction();
//        try {
//            List<RealmSaleActivity> realmSaleActivities = realm.where(RealmSaleActivity.class).equalTo("id", id).findAll();
//            if (realmSaleActivities.size() <= 0) {
//                return false;
//            }
//            realmSaleActivities.clear();
//            return true;
//        } finally {
//            realm.commitTransaction();
//            realm.close();
//        }
    }

    //java 8 Uses jack
    public List<SaleActivity> getSaleActivitiesByName(String name) {
        List<SaleActivity> realSaleActivities = new ArrayList<>();
        for (RealmSaleActivity realmSaleActivity : saleActivities) {
            if (realmSaleActivity.getSaleName().equals(name)) {
                realSaleActivities.add(new SaleActivity(realmSaleActivity));
            }
        }
        return realSaleActivities;
//        realm = Realm.getInstance(context);
//        realm.beginTransaction();
//        try {
//            List<SaleActivity> saleActivities = new ArrayList<>();
//            for (RealmSaleActivity realmSaleActivity : realm.where(RealmSaleActivity.class).findAll()) {
//                if (realmSaleActivity.getSaleName().equals(name)) {
//                    saleActivities.add(new SaleActivity(realmSaleActivity));
//                }
//            }
//            return saleActivities;
////            return realm.where(RealmSaleActivity.class).findAll()
////                    .stream()
////                    .map((realmSaleActivity -> new SaleActivity(realmSaleActivity)))
////                    .filter((saleActivity -> saleActivity.getSaleName().equals(name)))
////                    .collect(Collectors.toList());
//        } finally {
//            realm.commitTransaction();
//            realm.close();
//        }
    }

    public List<SaleActivityTime> getAllSaleActivitiesTimes() {
        return getSaleActivityTimes(getSaleActivities());
    }

    public List<SaleActivityTime> getSaleActivitiesTimesByName(String name) {
        return getSaleActivityTimes(getSaleActivitiesByName(name));
    }

    private List<SaleActivityTime> getSaleActivityTimes(List<SaleActivity> saleActivities) {
        List<SaleActivityTime> saleActivityTimes = new ArrayList<>();
        if (saleActivities.size() <= 0) {
            return saleActivityTimes;
        }
        Collections.sort(saleActivities, new Comparator<SaleActivity>() {
            @Override
            public int compare(SaleActivity o1, SaleActivity o2) {
                return o1.getBeginMoment().compareTo(o2.getBeginMoment());
            }
        });
        SaleActivityTime saleActivityTime = new SaleActivityTime();
        Iterator<SaleActivity> it = saleActivities.iterator();
        SaleActivity saleActivity = it.next();
        saleActivityTime.setDay(getBeginningOfDay(saleActivity.getBeginMoment()));
        saleActivityTime.addActivityTime(saleActivity);
        for (; it.hasNext();) {
            saleActivity = it.next();
            if (getBeginningOfDay(saleActivity.getBeginMoment()).compareTo(saleActivityTime.getDay()) == 0) {
                saleActivityTime.addActivityTime(saleActivity);
            } else {
                saleActivityTimes.add(saleActivityTime);
                saleActivityTime = new SaleActivityTime();
                saleActivityTime.setDay(saleActivity.getBeginMoment());
                saleActivityTime.addActivityTime(saleActivity);
            }
        }
        saleActivityTimes.add(saleActivityTime);
        return saleActivityTimes;
    }

    private GregorianCalendar getBeginningOfDay(GregorianCalendar calendar) {
        return new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    }
}
