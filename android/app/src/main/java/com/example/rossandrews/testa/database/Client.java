package com.example.rossandrews.testa.database;

import android.provider.BaseColumns;

/**
 * Created by Ross Andrews on 11/9/2016.
 */
/*
public class Client{
    private Client(){

    }

    public static class ClientEntry implements BaseColumns{
        public static final String TABLE_NAME = "client";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_EMAIL = "email";
    }
}
*/

public class Client {
    private int id;
    private String username;
    private String password;
    private String email;
    private Integer money;

    public Client(int id, String username, String password, String email, Integer money){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.money = money;
    }

    public Client(ClientRealm clientRealm){
        id = clientRealm.getId();
        username = clientRealm.getUsername();
        password = clientRealm.getPassword();
        email = clientRealm.getEmail();
        money = clientRealm.getMoney();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }


    @Override
    public String toString() {
        return "Client{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
