package edu.cmu.cs.cs214.hw5.operationplugins;

import edu.cmu.cs.cs214.hw5.core.BinaryOperationPlugin;
import edu.cmu.cs.cs214.hw5.core.Framework;
import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The abstract AbstractBinaryOperationPlugin class uses the template method pattern
 * where getMenuItemHelper and arithmetic are the customizable algorithm and the primitive
 * operations is compute
 */
public abstract class AbstractBinaryOperationPlugin implements BinaryOperationPlugin {
    static TimeSeries arithmetic(TimeSeries ts1, TimeSeries ts2, BinaryDoubleOperation op, String opName) {
        TimeSeries result = new TimeSeries(ts1.getName() + opName + ts2.getName());
        Set<LocalDate> intersection = ts1.getTimeSpan();
        intersection.retainAll(ts2.getTimeSpan());
        for (LocalDate time : intersection) {
            double opResult = op.compute(ts1.getValue(time), ts2.getValue(time));
            if (opResult == Double.POSITIVE_INFINITY) continue;
            result.insert(time, opResult);
        }
        return result;
    }

    /**
     * Gets the menu item whose actions listener runs compute on the selected time series
     * @param framework the registered framework
     * @param frame the framework GUI frame
     * @param menuItemName the menu item name
     * @return the menu item whose actions listener runs compute on the selected time series
     */
    public JMenuItem getMenuItemHelper(Framework framework, JFrame frame, String menuItemName) {
        JMenuItem opMenuItem = new JMenuItem(menuItemName);
        opMenuItem.addActionListener(e -> {
            List<TimeSeries> tsList = framework.getCurrentlySelectedTimeSeriesList();
            if (tsList.size() != 2) {
                JOptionPane.showMessageDialog(frame, "Must choose exactly 2 time series");
            } else {
                TimeSeries val = compute(tsList.get(0), tsList.get(1));
                DataSet ds = new DataSet(Collections.singletonList(val), Collections.emptyList(), menuItemName);
                framework.addDataSet(ds);
            }
        });
        return opMenuItem;
    }
}
