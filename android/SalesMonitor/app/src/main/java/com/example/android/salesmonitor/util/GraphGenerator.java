package com.example.android.salesmonitor.util;

import android.content.Context;
import android.graphics.Color;
import android.widget.FrameLayout;

import com.example.android.salesmonitor.SalesMonitorApp;
import com.example.android.salesmonitor.domain.SaleActivityTime;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Date;
import java.util.List;

/**
 * Created by Sergiu on 16.11.2016.
 */

public class GraphGenerator {

    public static void populateGraphWithAllSales(Context context, FrameLayout frameLayout) {
        populateGraph(context, frameLayout, SalesMonitorApp.getInstance().getDataStoreManager().getAllSaleActivitiesTimes());
    }

    public static void populateGraphWithOneSale(Context context, FrameLayout frameLayout, String saleName) {
        populateGraph(context, frameLayout, SalesMonitorApp.getInstance().getDataStoreManager().getSaleActivitiesTimesByName(saleName));
    }

    private static void populateGraph(Context context, FrameLayout frameLayout, List<SaleActivityTime> saleActivityTimes) {
        TimeSeries timeSeries = new TimeSeries("Minutes practicing sale");
        long max = 0;
        for (SaleActivityTime saleActivityTime : saleActivityTimes) {
            timeSeries.add(new Date(saleActivityTime.getDay().getTimeInMillis()), (double) saleActivityTime.getTotalMinutes());
            if (saleActivityTime.getTotalMinutes() > max) {
                max = saleActivityTime.getTotalMinutes();
            }
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(timeSeries);

        XYSeriesRenderer timesRenderer = new XYSeriesRenderer();
        timesRenderer.setColor(Color.BLUE);
        timesRenderer.setPointStyle(PointStyle.CIRCLE);
        timesRenderer.setFillPoints(true);
        timesRenderer.setLineWidth(4);
        timesRenderer.setDisplayChartValues(true);
        timesRenderer.setChartValuesTextSize(30);

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setChartTitle("Sale Times Chart");
        multiRenderer.setXTitle("Days");
        multiRenderer.setYTitle("Minutes");
        multiRenderer.setMarginsColor(Color.argb(0, 128, 0, 0)); // transparent margins
//        multiRenderer.setZoomButtonsVisible(true);
        multiRenderer.setDisplayValues(true);
        multiRenderer.setPanEnabled(false, false);
        multiRenderer.setYAxisMax(max + max / 20);
        multiRenderer.setYAxisMin(0);
        multiRenderer.setShowGrid(true); // we show the grid
        multiRenderer.setChartTitleTextSize(40);
        multiRenderer.setLabelsTextSize(30);
        multiRenderer.setLegendTextSize(30);
        multiRenderer.setAxisTitleTextSize(30);

        // Adding visitsRenderer and viewsRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(timesRenderer);

        GraphicalView chartView = (GraphicalView) ChartFactory.getTimeChartView(context, dataset, multiRenderer,"dd-MMM-yyyy");

        frameLayout.addView(chartView);
    }
}
