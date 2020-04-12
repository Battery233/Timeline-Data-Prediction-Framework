package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;

public interface AggregateOperationPlugin {
    /**
     * Perform the aggregation operation on a time series.
     * @param ts the time series
     * @return a single value as the result of the aggregation
     */
    double compute(TimeSeries ts);

    /**
     * Return a JMenuItem that will be displayed on the GUI. It should pre-include
     * action listeners on clicking to the item and getting required information from the
     * user, performing the computation, and calling framework methods if it wishes to
     * add generated data to the framework or display the generated data.
     * @param framework the main framework
     * @param frame the JFrame framework's main interface is rendered on
     * @return a JMenuItem that has the required action listeners.
     */
    JMenuItem getMenuItem(Framework framework, JFrame frame);

    /**
     * Return the name of the operation.
     * @return the name of the operation.
     */
    String getOpName();
}
