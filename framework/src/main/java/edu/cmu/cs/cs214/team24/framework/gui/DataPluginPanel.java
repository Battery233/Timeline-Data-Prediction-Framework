package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.BrowsePanel;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.DatePanel;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.ParamsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class DataPluginPanel extends JPanel {

    private Framework core;
    private JLabel statusLabel = new JLabel("Data not ready, please get data.");
    private BrowsePanel browsePanel;
    private ParamsPanel paramsPanel;
    private Date startDate, endDate;

    public DataPluginPanel(Framework framework){
        core = framework;

        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        add(statusLabel);
        browsePanel = new BrowsePanel(this, true);
        add(browsePanel);

        JPanel fullParamsPanel = new JPanel();
        // start/end date panels
        fullParamsPanel.setLayout(new FlowLayout());
        DatePanel datePanel1 = new DatePanel(this, true);
        fullParamsPanel.add(datePanel1);
        DatePanel datePanel2 = new DatePanel(this, false);
        fullParamsPanel.add(datePanel2);

        // params panel
        paramsPanel = new ParamsPanel(this);
        fullParamsPanel.add(paramsPanel);
        add(fullParamsPanel);

        JButton getDataButton = getDataButton();
        add(getDataButton);
    }

    public void onPluginRegistered(Plugin plugin) {
        browsePanel.onPluginRegistered(plugin);
    }

    public void onDateChosen(Date date, boolean isStart){
        if (isStart) startDate = date;
        else endDate = date;
    }

    public void onPluginChanged(Plugin dataPlugin){
        core.setCurrentDataPlugin(dataPlugin);
        Map<String, List<String>> paramOptions = core.getParamOptions(true);
        /** added for test */
        Map<String, Boolean> paramsMultiple = new HashMap<>();
        paramsMultiple.put("base", false);
        paramsMultiple.put("symbols", true);
        /** added for test */
//        Map<String, Boolean> paramsMultiple = core.getAreDataParamsMultiple(true);
        paramsPanel.refresh(paramOptions, paramsMultiple);
    }

    private JButton getDataButton(){
        JButton b = new JButton("Get data");
        b.addActionListener(e -> {
            Map<String, List<String>> paramValues = paramsPanel.getParamsValues();
            boolean configSuccess = core.setPluginParameters(
                    true, paramValues, startDate, endDate);
            if (!configSuccess) {
                statusLabel.setText("Invalid configuration. Please re-check dates and parameters.");
            }
            else {
                boolean getDataSuccess = core.getData();
                if (getDataSuccess) {
                    statusLabel.setText("Data ready. Proceed to display data.");
                } else {
                    statusLabel.setText("Get data failed. Please modify your configuration.");
                }
            }
        });
        return b;
    }

}
