package com.example.android.salesmonitor.util;

import com.example.android.salesmonitor.domain.SaleActivity;

import java.util.Calendar;

/**
 * Created by Sergiu on 17.11.2016.
 */

public class SaleActivityHelper {
    private SaleActivityHelper() {}

    public static String toString(SaleActivity saleActivity) {
        return saleActivity.getSaleName() + " " + saleActivity.getBeginMoment().get(Calendar.DAY_OF_MONTH) + "/" + saleActivity.getBeginMoment().get(Calendar.MONTH) +
                "/" + saleActivity.getBeginMoment().get(Calendar.YEAR) + " " + saleActivity.getBeginMoment().get(Calendar.HOUR) + ":" +
                String.format("%02d", saleActivity.getBeginMoment().get(Calendar.MINUTE)) + " - " + saleActivity.getEndMoment().get(Calendar.HOUR) + ":" +
                String.format("%02d", saleActivity.getEndMoment().get(Calendar.MINUTE));
    }
}
