package edu.cmu.cs.cs214.hw5.framework.core;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * A class represents the data we extracted from the data plugin.
 * This will be used to pass on information from the data plugin to the framework.
 */
public class DataSet {
    //a date array to indicate the time range of the date
    // e.g., from 2020-04-01 to 2020-04-03
    private final Date[] timeRange;

    //a map to store a group of data. The string represent the name of the data (e.g. oil price),
    // the double array stores the actual daily data. The length of the double[] array should be same as the
    // timeRange array.
    private final Map<String, double[]> data;

    public DataSet(Date[] timeRange, Map<String, double[]> data) {
        this.timeRange = timeRange;
        this.data = data;
    }

    public Date[] getTimeRange() {
        return timeRange;
    }

    public Map<String, double[]> getData() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("timeRange=").append(Arrays.toString(timeRange)).append("\n");
        for (Map.Entry<String, double[]> e : data.entrySet()) {
            sb.append(e.getKey()).append(": ");
            for (double d : e.getValue()) {
                sb.append(d).append(", ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
