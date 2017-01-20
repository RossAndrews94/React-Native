package com.example.rossandrews.testa.database;

import io.realm.RealmObject;

/**
 * Created by Ross Andrews on 1/19/2017.
 */

public class ClientRealm extends RealmObject {
    private int id;
    private String username;
    private String password;
    private String email;
    private int money;

    public ClientRealm(){

    }

    public ClientRealm(Client client){
        id = client.getId();
        username = client.getUsername();
        password = client.getPassword();
        email = client.getEmail();
        money = client.getMoney();
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
