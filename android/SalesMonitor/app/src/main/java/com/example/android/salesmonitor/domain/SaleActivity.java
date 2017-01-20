package com.example.android.salesmonitor.domain;

import com.example.android.salesmonitor.realm.RealmSaleActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.util.Calendar.HOUR_OF_DAY;

/**
 * Created by Sergiu on 22.10.2016.
 */
public class SaleActivity {
    private int id;
    private String saleName;
    private GregorianCalendar beginMoment;
    private GregorianCalendar endMoment;

    public SaleActivity() {
    }

    public SaleActivity(int id, String saleName, GregorianCalendar beginMoment, GregorianCalendar endMoment) {
        this.beginMoment = beginMoment;
        this.endMoment = endMoment;
        this.saleName = saleName;
        this.id = id;
    }

    public SaleActivity(RealmSaleActivity realmSaleActivity) {
        id = realmSaleActivity.getId();
        saleName = realmSaleActivity.getSaleName();
        beginMoment = new GregorianCalendar(
                realmSaleActivity.getYear(), realmSaleActivity.getMonth(), realmSaleActivity.getDay(),
                realmSaleActivity.getBeginHour(), realmSaleActivity.getBeginMinute());
        endMoment = new GregorianCalendar(
                realmSaleActivity.getYear(), realmSaleActivity.getMonth(), realmSaleActivity.getDay(),
                realmSaleActivity.getEndHour(), realmSaleActivity.getEndMinute());
    }

    public GregorianCalendar getBeginMoment() {
        return beginMoment;
    }

    public void setBeginMoment(GregorianCalendar beginMoment) {
        this.beginMoment = beginMoment;
    }

    public GregorianCalendar getEndMoment() {
        return endMoment;
    }

    public void setEndMoment(GregorianCalendar endMoment) {
        this.endMoment = endMoment;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return saleName + " " + beginMoment.get(Calendar.DAY_OF_MONTH) + "/" + beginMoment.get(Calendar.MONTH) +
                "/" + beginMoment.get(Calendar.YEAR) + " " + beginMoment.get(HOUR_OF_DAY) + ":" +
                String.format("%02d", beginMoment.get(Calendar.MINUTE)) + " - " + endMoment.get(HOUR_OF_DAY) + ":" +
                String.format("%02d", endMoment.get(Calendar.MINUTE));
    }
}
