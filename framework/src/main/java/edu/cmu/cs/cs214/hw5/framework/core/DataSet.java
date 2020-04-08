package edu.cmu.cs.cs214.hw5.framework.core;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * A class represents the data we extracted from the data plugin
 */
public class DataSet {
    private final Date[] timeRange;
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
