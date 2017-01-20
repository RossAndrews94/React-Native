package com.example.android.salesmonitor.util;

/**
 * Created by Sergiu on 09.01.2017.
 */

public final class FirebaseNames {
    public static final String COACH_ROLE = "shop";
    public static final String REG_USER_ROLE = "reg_user";
    public static final String COACHES = "shopes";
    public static final String REG_USERS = "reg_users";

    public static String getInfoName(String email) {
        email = email.split("@")[0];
        return email + "-info";
    }

    public static String getActivitiesName(String email) {
        email = email.split("@")[0];
        return email + "-activities";
    }

    public static String getUserRole(String firebaseUserRole) {
        if (firebaseUserRole.equals(COACH_ROLE)) {
            return "Shop";
        }
        return "Regular User";
    }

    public static String getShopName(String email) {
        email = email.split("@")[0];
        return email + "-shop";
    }

    public static String getGmailAddress(String email) {
        if (!email.contains("@")) {
            email += "@gmail.com";
        }
        return email;
    }

    public static String getClientsName(String email) {
        email = email.split("@")[0];
        return email + "-clients";
    }
}
