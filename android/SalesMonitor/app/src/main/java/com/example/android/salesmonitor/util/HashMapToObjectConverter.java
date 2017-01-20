package com.example.android.salesmonitor.util;

import com.example.android.salesmonitor.realm.RealmSaleActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sergiu on 09.01.2017.
 */

public final class HashMapToObjectConverter {
    public static RealmSaleActivity convertHashMapToObject(HashMap<String, Object> map) {
        RealmSaleActivity realmSaleActivity = new RealmSaleActivity();

        realmSaleActivity.setBeginHour(((Long)map.get("beginHour")).intValue());
        realmSaleActivity.setBeginMinute(((Long)map.get("beginMinute")).intValue());
        realmSaleActivity.setDay(((Long)map.get("day")).intValue());
        realmSaleActivity.setEndHour(((Long)map.get("endHour")).intValue());
        realmSaleActivity.setEndMinute(((Long)map.get("endMinute")).intValue());
        realmSaleActivity.setId(((Long)map.get("id")).intValue());
        realmSaleActivity.setMonth(((Long)map.get("month")).intValue());
        realmSaleActivity.setYear(((Long)map.get("year")).intValue());
        realmSaleActivity.setSaleName((String) map.get("saleName"));
        return realmSaleActivity;
    }

    public static List<RealmSaleActivity> convertHashMapListToObjectList(List<HashMap<String, Object>> maps) {
        List<RealmSaleActivity> realmSaleActivities = new ArrayList<>();
        for (HashMap<String, Object> map: maps) {
            if (map != null) {
                realmSaleActivities.add(convertHashMapToObject(map));
            }
        }
        return realmSaleActivities;
    }
}
