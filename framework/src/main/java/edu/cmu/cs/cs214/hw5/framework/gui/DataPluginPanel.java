package edu.cmu.cs.cs214.hw5.framework.gui;

import edu.cmu.cs.cs214.hw5.framework.core.Framework;
import edu.cmu.cs.cs214.hw5.framework.core.Plugin;
import edu.cmu.cs.cs214.hw5.framework.gui.plugin.DatePanel;

import java.util.*;

public class DataPluginPanel extends PluginPanel {

    public DataPluginPanel(MainPanel parent, Framework framework){
        super(parent, framework, true);
        addStatusPanel();
        addBrowsePanel();
        addDatePanels();
        addParamsPanel();
        addButtonPanel();
    }

    private void addDatePanels(){
        DatePanel datePanel1 = new DatePanel(this, true);
        add(datePanel1);
        DatePanel datePanel2 = new DatePanel(this, false);
        add(datePanel2);
    }

    public void onDateChosen(Date date, boolean isStart){
        if (isStart) startDate = date;
        else endDate = date;
    }

    @Override
    public void onPluginChanged(Plugin plugin){
        core.setCurrentDataPlugin(plugin);
        statusLabel.setText("Data not ready, please configure data parameters and get data.");
        parent.onDataPluginChanged();
        retrieveParams();
        refreshParams();
    }

}
