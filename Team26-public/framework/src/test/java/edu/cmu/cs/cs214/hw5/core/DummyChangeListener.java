package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;

import javax.swing.*;

public class DummyChangeListener implements FrameworkChangeListener {
    boolean onNewChartCalled;
    boolean onDataSetAddedCalled;

    @Override
    public void onNewChart(JPanel chart, boolean isTimeSeriesChart) {
        onNewChartCalled = true;
    }

    @Override
    public void onDataSetAdded(DataSet dataSet, boolean isGen) {
        onDataSetAddedCalled = true;
    }
}
