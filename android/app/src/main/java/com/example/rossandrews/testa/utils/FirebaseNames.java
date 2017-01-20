package com.example.rossandrews.testa.utils;

/**
 * Created by Ross Andrews on 1/19/2017.
 */

public class FirebaseNames {
    public static final String CLIENT_ROLE = "client";
    public static final String REG_USER_ROLE = "reg_user";

    public static String getInfoName(String email){
        email = email.split("@")[0];
        return email + "-info";
    }

    public static String getActivitiesName(String email) {
        email = email.split("@")[0];
        return email + "-activities";
    }

    public static String getUserRole(String firebaseUserRole) {
        if (firebaseUserRole.equals(CLIENT_ROLE)) {
            return "Client";
        }
        return "Regular User";
    }

    public static String getClientName(String email) {
        email = email.split("@")[0];
        return email + "-client";
    }

    public static String getGmailAddress(String email) {
        if (!email.contains("@")) {
            email += "@gmail.com";
        }
        return email;
    }
}
