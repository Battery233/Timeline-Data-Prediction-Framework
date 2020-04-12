package edu.cmu.cs.cs214.hw5.operationplugins;

import edu.cmu.cs.cs214.hw5.core.AggregateOperationPlugin;
import edu.cmu.cs.cs214.hw5.core.Framework;
import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * The abstract AbstractAggregateOperationPlugin class uses the template method pattern
 * where getMenuItemHelper is the customizable algorithm and the primitive operations
 * are compute and getOpName
 */
public abstract class AbstractAggregateOperationPlugin implements AggregateOperationPlugin {
    /**
     * Gets the menu item whose actions listener runs compute on the selected time series
     * and sets the generated time series name to the original time series name + getOpName()
     * @param framework the registered framework
     * @param frame the framework GUI frame
     * @param menuItemName the menu item name
     * @return the menu item whose actions listener runs compute on the selected time series
     * and sets the generated time series name to the original time series name + getOpName()
     */
    public JMenuItem getMenuItemHelper(Framework framework, JFrame frame, String menuItemName) {
        JMenuItem opMenuItem = new JMenuItem(menuItemName);
        opMenuItem.addActionListener(e -> {
            List<TimeSeries> tsList = framework.getCurrentlySelectedTimeSeriesList();
            System.out.println(tsList);
            if (tsList.size() != 1) {
                JOptionPane.showMessageDialog(frame, "Must choose exactly 1 time series");
            } else {
                TimeSeries ts = tsList.get(0);
                double val = compute(ts);
                String tpName = ts.getName() + " " + getOpName();
                TimePoint tp = new TimePoint(LocalDate.of(1970, 1, 1), val, tpName);
                DataSet ds = new DataSet(Collections.emptyList(), Collections.singletonList(tp), menuItemName);
                framework.addDataSet(ds);
            }
        });
        return opMenuItem;
    }
}
