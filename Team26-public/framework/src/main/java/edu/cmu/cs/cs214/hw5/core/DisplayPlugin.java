package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;
import java.util.List;

/**
 * the display plugin interface for generating plots of either time series or time points.
 * Note that a display plugin must only support one type of chart. To add
 * more chart types, please implement a separate display plugins for each.
 * In addition, display plugins should convert their chart into JPanel before returning the
 * chart to the framework.
 */
public interface DisplayPlugin {
    /**
     * Gets the chart type name that the display plugin supports. For example,
     * a display plugin that implements line chart should return "Line Chart".
     *
     * @return The name of the chart type that the display plugin supports
     */
    String getChartTypeName();

    /**
     * Returns a boolean flag indicating whether the display plugins' chart type
     * supports time series or not. For example, a line chart display plugin
     * should return true (since line chart plots time series), while a bar chart
     * display plugin should return false (since bar chart plots time points).
     *
     * @return true if the display plugin's chart type supports time series, false if
     * the display plugin's chart type supports time points.
     */
    Boolean isTimeSeriesChart();

    /**
     * Gets an empty chart with no data.
     *
     * @return an empty chart
     */
    JPanel getEmptyChart();

    /**
     * Plots the time series on the chart and return the chart as a JPanel.
     * If the display plugin's chart type does not support time series,
     * then return null. (for example, a display plugin implementing
     * heat map should return the empty chart since heat map cannot plot time
     * series)
     *
     * @param timeSeriesList the list of time series that needs to be plotted
     *                       It is guaranteed that every time series in this
     *                       list has the same time unit. (For example, it could
     *                       be a list of day-based time series)
     * @return the plotted chart showing all time series
     */
    JPanel getTimeSeriesChart(List<TimeSeries> timeSeriesList);

    /**
     * Plots the time points on the chart and return the chart as a JPanel
     * If the display plugin's chart type does not support time points,
     * then return null. (for example, a display plugin implementing
     * line chart should return the empty chart since line chart is meant to
     * plot time series)
     *
     * @param timePointList the list of time points that needs to be plotted
     * @return the plotted chart showing all time points
     */
    JPanel getTimePointChart(List<TimePoint> timePointList);
}
