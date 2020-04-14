package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;

import javax.swing.*;

public interface FrameworkChangeListener {
    /**
     * Called when a new chart is displayed
     *
     * @param chart             the new chart to be displayed
     * @param isTimeSeriesChart true if the chart is a time series chart,
     *                          false if the chart is  a point chart
     */
    void onNewChart(JPanel chart, boolean isTimeSeriesChart);

    /**
     * Called when a data set is added
     *
     * @param dataSet the added data set
     * @param isGen   true if the data set is generated from previous time
     *                series using the framework's reusable statistical utilities.
     *                false if the data set is directly extracted from a data plugin
     */
    void onDataSetAdded(DataSet dataSet, boolean isGen);
}
