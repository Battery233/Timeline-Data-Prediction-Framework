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

public class DataPluginPanel extends PluginPanel {

    public DataPluginPanel(MainPanel parent, Framework framework){
        super(parent, framework, true);
        addStatusLabel();
        addBrowsePanel();
        addDatePanels();
        addParamsPanel();
        addButton();
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

}
