package com.example.android.salesmonitor.realm;

import com.example.android.salesmonitor.domain.SaleActivity;

import java.util.Calendar;

import io.realm.RealmObject;

/**
 * Created by Sergiu on 17.11.2016.
 */

public class RealmSaleActivity extends RealmObject{
    private int id;
    private String saleName;
    private int year;
    private int month;
    private int day;
    private int beginHour;
    private int beginMinute;
    private int endHour;
    private int endMinute;

    public RealmSaleActivity() {
    }

    public RealmSaleActivity(SaleActivity saleActivity) {
        id = saleActivity.getId();
        saleName = saleActivity.getSaleName();
        year = saleActivity.getBeginMoment().get(Calendar.YEAR);
        month = saleActivity.getBeginMoment().get(Calendar.MONTH);
        day = saleActivity.getBeginMoment().get(Calendar.DAY_OF_MONTH);
        beginHour = saleActivity.getBeginMoment().get(Calendar.HOUR_OF_DAY);
        beginMinute = saleActivity.getBeginMoment().get(Calendar.MINUTE);
        endHour = saleActivity.getEndMoment().get(Calendar.HOUR_OF_DAY);
        endMinute = saleActivity.getEndMoment().get(Calendar.MINUTE);
    }

    public int getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(int beginHour) {
        this.beginHour = beginHour;
    }

    public int getBeginMinute() {
        return beginMinute;
    }

    public void setBeginMinute(int beginMinute) {
        this.beginMinute = beginMinute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
