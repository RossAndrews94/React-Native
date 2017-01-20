package com.example.android.salesmonitor.domain;

import java.util.GregorianCalendar;

/**
 * Created by Sergiu on 26.11.2016.
 */

public class SaleActivityTime {
    private GregorianCalendar day;
    private long totalMinutes;

    public SaleActivityTime(GregorianCalendar day, int totalMinutes) {
        this.day = day;
        this.totalMinutes = totalMinutes;
    }

    public SaleActivityTime() {
        this.totalMinutes = 0;
    }

    public GregorianCalendar getDay() {
        return day;
    }

    public void setDay(GregorianCalendar day) {
        this.day = day;
    }

    public long getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(long totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public void addActivityTime(SaleActivity saleActivity) {
        this.totalMinutes += (saleActivity.getEndMoment().getTimeInMillis() - saleActivity.getBeginMoment().getTimeInMillis()) / 60000;
    }
}
