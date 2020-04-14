package edu.cmu.cs.cs214.hw5.operationplugins;

import edu.cmu.cs.cs214.hw5.core.Framework;
import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DivPlugin extends AbstractBinaryOperationPlugin {
    /**
     * Compute the ratio of two time series
     *
     * @param ts1 the time series
     * @param ts2 the other time series
     * @return the ratio of the two time series
     */
    @Override
    public TimeSeries compute(TimeSeries ts1, TimeSeries ts2) {
        BinaryDoubleOperation div = (a, b) -> a / b;
        return arithmetic(ts1, ts2, div, getOpName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOpName() {
        return "/";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JMenuItem getMenuItem(Framework framework, JFrame frame) {
        JMenuItem ratioMenuItem = new JMenuItem("Ratio");
        ratioMenuItem.addActionListener(e -> {
            List<TimeSeries> tsList = framework.getCurrentlySelectedTimeSeriesList();
            if (tsList.size() != 2) {
                JOptionPane.showMessageDialog(frame, "Must choose exactly 2 time series");
            } else {
                JPanel selectPanel = new JPanel();
                selectPanel.add(new JLabel("Select Numerator: "));
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                model.addElement(tsList.get(0));
                model.addElement(tsList.get(1));
                JComboBox comboBox = new JComboBox(model);
                selectPanel.add(comboBox);
                int result = JOptionPane.showConfirmDialog(frame, selectPanel, "Select Numerator Time Series",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    TimeSeries numeratorTimeSeries = (TimeSeries) comboBox.getSelectedItem();
                    TimeSeries denominatorTimeSeries;
                    if (numeratorTimeSeries == tsList.get(0)) {
                        denominatorTimeSeries = tsList.get(1);
                    } else {
                        denominatorTimeSeries = tsList.get(0);
                    }
                    TimeSeries ratio = compute(numeratorTimeSeries, denominatorTimeSeries);
                    DataSet ds = new DataSet(Collections.singletonList(ratio), new ArrayList<>(), "Ratio of "
                            + numeratorTimeSeries.getName() + " against " + denominatorTimeSeries.getName());
                    framework.addDataSet(ds);
                    framework.plotTimeSeries(Collections.singletonList(ratio));
                }
            }
        });
        return ratioMenuItem;
    }
}
