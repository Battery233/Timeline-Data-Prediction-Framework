package edu.cmu.cs.cs214.hw5.operationplugins;

import edu.cmu.cs.cs214.hw5.core.Framework;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Map;

public class MinPlugin extends AbstractAggregateOperationPlugin {

    /**
     * {@inheritDoc}
     */
    @Override
    public double compute(TimeSeries ts) {
        double min = Double.MAX_VALUE;
        for (Map.Entry<LocalDate, Double> e : ts) {
            min = Double.min(min, e.getValue());
        }
        return min;
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
        return "Min";
    }
}
