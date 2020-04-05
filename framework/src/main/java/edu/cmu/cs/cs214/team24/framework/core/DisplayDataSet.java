package edu.cmu.cs.cs214.team24.framework.core;

import java.util.Date;
import java.util.Map;

public class DisplayDataSet {
    private final Date[] timeRange;
    private final Date[] timeRangeForPrediction;
    private final Map<String, double[]> data;
    private final Map<String, double[]> dataPredicted;

    public DisplayDataSet(Date[] timeRange, Map<String, double[]> data, Date[] timeRangeForPrediction, Map<String, double[]> dataPredicted) {
        this.timeRange = timeRange;
        this.data = data;
        this.timeRangeForPrediction = timeRangeForPrediction;
        this.dataPredicted = dataPredicted;
    }

    public Date[] getTimeRangeForPrediction() {
        return timeRangeForPrediction;
    }

    public Map<String, double[]> getDataPredicted() {
        return dataPredicted;
    }

    public Date[] getTimeRange() {
        return timeRange;
    }

    public Map<String, double[]> getData() {
        return data;
    }
}
