package edu.cmu.cs.cs214.hw5.operationplugins;

import edu.cmu.cs.cs214.hw5.core.Framework;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;

public class PlusPlugin extends AbstractBinaryOperationPlugin {
    /**
     * Compute the sum of two time series
     *
     * @param ts1 the time series
     * @param ts2 the other time series
     * @return the sum of the two time series
     */
    @Override
    public TimeSeries compute(TimeSeries ts1, TimeSeries ts2) {
        BinaryDoubleOperation plus = Double::sum;
        return arithmetic(ts1, ts2, plus, getOpName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOpName() {
        return "+";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JMenuItem getMenuItem(Framework framework, JFrame frame) {
        return getMenuItemHelper(framework, frame, "Sum");
    }
}
