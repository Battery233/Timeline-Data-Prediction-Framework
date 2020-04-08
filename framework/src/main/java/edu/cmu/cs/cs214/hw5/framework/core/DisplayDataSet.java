package edu.cmu.cs.cs214.hw5.framework.core;

import java.util.Date;
import java.util.Map;


/**
 * The Set of data for display.
 */
public class DisplayDataSet {

    private DataSet originalData;
    private Date predictionDate;
    private Map<String,Double> predictionValue;

    /**
     * The constructor of the display data set
     * @param originalData   the original data without any prediction
     * @param predictionDate  the date of the prediction
     * @param predictionValue  a map of the type of the prediction value and the prediction value
     */
    public DisplayDataSet(DataSet originalData, Date predictionDate, Map<String,Double> predictionValue) {
        this.originalData = originalData;
        this.predictionDate = predictionDate;
        this.predictionValue = predictionValue;
    }

    /**
     * To get the original data without any prediction
     * @return   the original data
     */
    public DataSet getOriginalData() {
        return originalData;
    }

    /**
     * To get the date of the prediction
     * @return   the date of the prediction
     */
    public Date getPredictionDate() {
        return predictionDate;
    }

    /**
     * To get the map of the type of the prediction value and the prediction value
     * @return a map of the type of the prediction value and the prediction value
     */
    public Map<String, Double> getPredictionValue() {
        return predictionValue;
    }
}
