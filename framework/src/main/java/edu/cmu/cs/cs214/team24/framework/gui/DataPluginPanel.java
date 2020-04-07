package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.BrowsePanel;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.DatePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class DataPluginPanel extends JPanel {

    private Framework core;
    private JLabel statusLabel = new JLabel("Data not ready, please get data.");
    private BrowsePanel browsePanel;
    private Calendar startDate;
    private Calendar endDate;

    public DataPluginPanel(Framework framework){
        core = framework;

        setLayout(new GridLayout(3, 1));
        add(statusLabel);
        browsePanel = new BrowsePanel(core, true);
        add(browsePanel);
        DatePanel datePanel = new DatePanel(this, true);
        add(datePanel);
    }

    public void onPluginRegistered(Plugin plugin) {
        browsePanel.onPluginRegistered(plugin);
    }

    public void onDateChosen(Calendar date, boolean isStart){
        if (isStart) startDate = date;
        else endDate = date;
    }

}
