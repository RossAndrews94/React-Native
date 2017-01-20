package com.example.rossandrews.testa.utils;

import com.example.rossandrews.testa.database.ClientRealm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by RossAndrews on 09.01.2017.
 */

public final class HashMapToObjectConverter {
    public static ClientRealm convertHashMapToObject(HashMap<String, Object> map) {
        ClientRealm clientRealm = new ClientRealm();
        clientRealm.setUsername((String) map.get("username").toString());
        clientRealm.setPassword((String) map.get("password").toString());
        clientRealm.setEmail((String) map.get("email").toString());
        clientRealm.setMoney(((Long)map.get("money")).intValue());

        return clientRealm;
    }

    public static List<ClientRealm> convertHashMapListToObjectList(List<HashMap<String, Object>> maps) {
        List<ClientRealm> clientRealms = new ArrayList<>();
        for (HashMap<String, Object> map: maps) {
            if (map != null) {
                clientRealms.add(convertHashMapToObject(map));
            }
        }
        return clientRealms;
    }
}
