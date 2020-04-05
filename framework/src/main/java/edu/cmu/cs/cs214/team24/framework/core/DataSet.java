package edu.cmu.cs.cs214.team24.framework.core;

import java.util.Date;
import java.util.Map;

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
}
