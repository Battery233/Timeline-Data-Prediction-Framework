package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.BrowsePanel;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.DatePanel;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.ParamsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DataPluginPanel extends JPanel {

    private Framework core;
    private JLabel statusLabel = new JLabel("Data not ready, please get data.");
    private BrowsePanel browsePanel;
    private Calendar startDate;
    private Calendar endDate;
    private Map<String, List<String>> paramValues;

    public DataPluginPanel(Framework framework){
        core = framework;

        setLayout(new GridLayout(4, 1));
        add(statusLabel);
        browsePanel = new BrowsePanel(core, true);
        add(browsePanel);

        JPanel fullParamsPanel = new JPanel();
        // start/end date panels
        fullParamsPanel.setLayout(new FlowLayout());
        DatePanel datePanel1 = new DatePanel(this, true);
        fullParamsPanel.add(datePanel1);
        DatePanel datePanel2 = new DatePanel(this, false);
        fullParamsPanel.add(datePanel2);

        // params panel
        Map<String, List<String>> paramOptions = core.getParamOptions(true);
        ParamsPanel paramsPanel = new ParamsPanel(this, paramOptions);
        fullParamsPanel.add(paramsPanel);
        add(fullParamsPanel);

        JButton getDataButton = new JButton("Get data.");
        add(getDataButton);
    }

    public void onPluginRegistered(Plugin plugin) {
        browsePanel.onPluginRegistered(plugin);
    }

    public void onDateChosen(Calendar date, boolean isStart){
        if (isStart) startDate = date;
        else endDate = date;
    }

    public void onParamSelected(String param, String option){

    }

}
