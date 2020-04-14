package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FrameworkImpl implements Framework {
    //made package private for testing purpose
    FrameworkChangeListener changeListener;
    final List<BinaryOperationPlugin> binaryOperationPlugins = new ArrayList<>();
    final List<AggregateOperationPlugin> aggregateOperationPlugins = new ArrayList<>();
    final List<DataPlugin> dataPlugins = new ArrayList<>();
    final List<DisplayPlugin> displayPlugins = new ArrayList<>();
    DisplayPlugin currentDisplayPlugin;
    List<TimeSeries> currentlySelectedTimeSeriesList = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void newChart(DisplayPlugin displayPlugin) {
        currentDisplayPlugin = displayPlugin;
        JPanel emptyChart = displayPlugin.getEmptyChart();
        notifyNewChart(emptyChart, displayPlugin.isTimeSeriesChart());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDataSet(DataPlugin dataPlugin, String dataSetName) {
        DataSet ds = dataPlugin.getDataSet(dataSetName);
        notifyDataSetAdded(ds, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDataSet(DataSet ds) {
        notifyDataSetAdded(ds, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setChangeListener(FrameworkChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerBinaryOperationPlugin(BinaryOperationPlugin binaryOperationPlugin) {
        binaryOperationPlugins.add(binaryOperationPlugin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerAggregateOperationPlugin(AggregateOperationPlugin aggregateOperationPlugin) {
        aggregateOperationPlugins.add(aggregateOperationPlugin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerDisplayPlugin(DisplayPlugin displayPlugin) {
        displayPlugins.add(displayPlugin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerDataPlugin(DataPlugin dataPlugin) {
        dataPlugins.add(dataPlugin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void plotTimeSeries(List<TimeSeries> timeSeries) {
        JPanel newChart;
        if (timeSeries.isEmpty()) {
            newChart = currentDisplayPlugin.getEmptyChart();
        } else {
            newChart = currentDisplayPlugin.getTimeSeriesChart(timeSeries);
        }
        notifyNewChart(newChart, currentDisplayPlugin.isTimeSeriesChart());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void plotTimePoint(List<TimePoint> timePoints) {
        JPanel newChart;
        if (timePoints.isEmpty()) {
            newChart = currentDisplayPlugin.getEmptyChart();
        } else {
            newChart = currentDisplayPlugin.getTimePointChart(timePoints);
        }
        notifyNewChart(newChart, currentDisplayPlugin.isTimeSeriesChart());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BinaryOperationPlugin> getBinaryOperationPluginList() {
        return new ArrayList<>(binaryOperationPlugins);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AggregateOperationPlugin> getAggregateOperationPluginList() {
        return new ArrayList<>(aggregateOperationPlugins);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DisplayPlugin> getDisplayPluginList() {
        return new ArrayList<>(displayPlugins);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataPlugin> getDataPluginList() {
        return new ArrayList<>(dataPlugins);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrentlySelectedTimeSeriesList(List<TimeSeries> tsList) {
        currentlySelectedTimeSeriesList = new ArrayList<>(tsList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TimeSeries> getCurrentlySelectedTimeSeriesList() {
        return new ArrayList<>(currentlySelectedTimeSeriesList);
    }

    private void notifyNewChart(JPanel chart, boolean isTimeSeriesChart) {
        changeListener.onNewChart(chart, isTimeSeriesChart);
    }

    private void notifyDataSetAdded(DataSet dataSet, boolean isGen) {
        changeListener.onDataSetAdded(dataSet, isGen);
    }

}
