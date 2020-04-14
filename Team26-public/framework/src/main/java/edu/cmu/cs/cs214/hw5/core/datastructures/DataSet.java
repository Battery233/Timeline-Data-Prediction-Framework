package edu.cmu.cs.cs214.hw5.core.datastructures;

import java.util.*;

/**
 * The Data Set class that represents a data set.
 * A data set is a collection of time series and time points.
 */
public class DataSet {
    private final Map<String, TimeSeries> name2timeseries = new HashMap<>();
    private final Map<String, TimePoint> name2timepoint = new HashMap<>();
    private final String name;

    /**
     * the constant empty data set
     */
    public static final DataSet EMPTY_DATASET = new DataSet(Collections.emptyList(), Collections.emptyList(),
            "EMPTY DATASET");

    /**
     * Constructs a data set containing the given list of time series and the given list of time points.
     * The data set's name is set to the given string.
     *
     * @param timeSeriesList the list of time series in the data set (empty list if the data set does not
     *                       contain time series)
     * @param timePointList  the list of time points in the data set (empty list if the data set does not
     *                       contain time points)
     * @param name           the name of the data set
     */
    public DataSet(List<TimeSeries> timeSeriesList, List<TimePoint> timePointList, String name) {
        this.name = name;
        for (TimeSeries timeSeries : timeSeriesList) {
            name2timeseries.put(timeSeries.getName(), timeSeries);
        }
        for (TimePoint timePoint : timePointList) {
            name2timepoint.put(timePoint.getName(), timePoint);
        }
    }

    /**
     * Gets the list of time series stored in the data set
     *
     * @return the list of time series stored in the data set
     */
    public Collection<TimeSeries> getTimeSeries() {
        return name2timeseries.values();
    }

    /**
     * Gets the list of time points stored in the data set
     *
     * @return the list of time points stored in the data set
     */
    public Collection<TimePoint> getTimePoints() {
        return name2timepoint.values();
    }
}