package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;

public interface BinaryOperationPlugin {
    /**
     * Perform the binary operation on two TimeSeries.
     * @param ts1 the first operand
     * @param ts2 the second operand
     * @return the result of the binary operation
     */
    TimeSeries compute(TimeSeries ts1, TimeSeries ts2);

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
     * @return the name of the operation
     */
    String getOpName();
}
