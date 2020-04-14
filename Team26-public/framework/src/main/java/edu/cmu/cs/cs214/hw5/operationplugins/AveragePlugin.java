package edu.cmu.cs.cs214.hw5.operationplugins;

import edu.cmu.cs.cs214.hw5.core.Framework;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Map;

public class AveragePlugin extends AbstractAggregateOperationPlugin {
    /**
     * Compute the average of the time series
     * @param ts the time series
     * @return the average of the values of time points
     */
    @Override
    public double compute(TimeSeries ts) {
        double sum = 0;
        int count = 0;
        for (Map.Entry<LocalDate, Double> e : ts) {
            sum += e.getValue();
            count++;
        }
        return sum / count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JMenuItem getMenuItem(Framework framework, JFrame frame) {
        return getMenuItemHelper(framework, frame, getOpName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOpName() {
        return "Average";
    }
}
