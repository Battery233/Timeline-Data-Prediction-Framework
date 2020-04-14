package edu.cmu.cs.cs214.hw5.operationplugins;

import edu.cmu.cs.cs214.hw5.core.Framework;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;

public class MultiplyPlugin extends AbstractBinaryOperationPlugin {
    /**
     * Compute the product of two time series
     *
     * @param ts1 the time series
     * @param ts2 the other time series
     * @return the product of the two time series
     */
    @Override
    public TimeSeries compute(TimeSeries ts1, TimeSeries ts2) {
        BinaryDoubleOperation multiply = (a, b) -> a * b;
        return arithmetic(ts1, ts2, multiply, getOpName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOpName() {
        return "*";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JMenuItem getMenuItem(Framework framework, JFrame frame) {
        return getMenuItemHelper(framework, frame, "Product");
    }
}
