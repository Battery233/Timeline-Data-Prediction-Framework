package edu.cmu.cs.cs214.team24.framework.core;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DisplayDataSet {
//    private final Date[] timeRange;
//    private final Map<String, List<Double>> data;

    private DataSet originalData;
    private Date predictionDate;
    private Map<String,Double> predictionValue;

    public DisplayDataSet(DataSet originalData, Date predictionDate, Map<String,Double> predictionValue) {
        this.originalData = originalData;
        this.predictionDate = predictionDate;
        this.predictionValue = predictionValue;
    }

    public DataSet getOriginalData() {
        return originalData;
    }

    public Date getPredictionDate() {
        return predictionDate;
    }

    public Map<String, Double> getPredictionValue() {
        return predictionValue;
    }
}
