package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import java.util.List;

/**
 * The framework class that coordinates interactions between the plugins and the GUI
 */
public interface Framework {
    /**
     * Display a new empty chart using the given display plugin
     *
     * @param displayPlugin the display plugin
     */
    void newChart(DisplayPlugin displayPlugin);

    /**
     * Extract a data set from a data plugin and add the data set to the framework
     *
     * @param dataPlugin  the data plugin to extract the date set from
     * @param dataSetName the string representation of the data set (for example: file path
     *                    data name, etc.)
     */
    void addDataSet(DataPlugin dataPlugin, String dataSetName);

    /**
     * Add a data set to the framework
     *
     * @param ds the data set to add
     */
    void addDataSet(DataSet ds);

    /**
     * Set the change listener (e.g. gui) of the framework
     *
     * @param changeListener the change listener
     */
    void setChangeListener(FrameworkChangeListener changeListener);

    /**
     * Register a binary operation plugin for the framework
     *
     * @param binaryOperationPlugin the binary operation plugin
     */
    void registerBinaryOperationPlugin(BinaryOperationPlugin binaryOperationPlugin);

    /**
     * Register an aggregateOperationPlugin plugin for the framework
     *
     * @param aggregateOperationPlugin the aggregate operation plugin
     */
    void registerAggregateOperationPlugin(AggregateOperationPlugin aggregateOperationPlugin);

    /**
     * Register a display plugin for the framework
     *
     * @param displayPlugin the display plugin
     */
    void registerDisplayPlugin(DisplayPlugin displayPlugin);

    /**
     * Register a data plugin for the framework
     *
     * @param dataPlugin the data plugin
     */
    void registerDataPlugin(DataPlugin dataPlugin);

    /**
     * Plot a list of time series using the current display plugin
     *
     * @param timeSeries the list of time series
     */
    void plotTimeSeries(List<TimeSeries> timeSeries);

    /**
     * Plot a list of time points using the current display plugin
     *
     * @param timePoints the list of time points
     */
    void plotTimePoint(List<TimePoint> timePoints);

    /**
     * Gets the list of binary operation plugin registered in the framework
     *
     * @return the list of binary operation plugin registered in the framework
     */
    List<BinaryOperationPlugin> getBinaryOperationPluginList();

    /**
     * Gets the list of aggregate operation plugin registered in the framework
     *
     * @return the list of aggregate operation plugin registered in the framework
     */
    List<AggregateOperationPlugin> getAggregateOperationPluginList();

    /**
     * Gets the list of data plugin registered in the framework
     *
     * @return the list of data plugin registered in the framework
     */
    List<DisplayPlugin> getDisplayPluginList();

    /**
     * Gets the list of display plugin registered in the framework
     *
     * @return the list of display plugin registered in the framework
     */
    List<DataPlugin> getDataPluginList();

    /**
     * Set the currently selected TimeSeries of the user
     * @param tsList the currently selected TimeSeries of the user
     */
    void setCurrentlySelectedTimeSeriesList(List<TimeSeries> tsList);

    /**
     * Get the currently selected TimeSeries of the user
     * @return the currently selected TimeSeries of the user
     */
    List<TimeSeries> getCurrentlySelectedTimeSeriesList();

}
