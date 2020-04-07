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
    private Calendar startDate;
    private Calendar endDate;
    private Map<String, List<String>> paramValues = new HashMap<>();

    public DataPluginPanel(Framework framework){
        core = framework;

        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
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
        /** added for test */
        Map<String, List<String>> paramOptions = new HashMap<>();
        Map<String, Boolean> paramsMultiple = new HashMap<>();
        String[] bases = {"EUR", "USD", "CNY"};
        paramOptions.put("base", Arrays.asList(bases));
        paramOptions.put("currency", Arrays.asList(bases));
        paramsMultiple.put("base", false);
        paramsMultiple.put("currency", true);
        /** added for test */
//        Map<String, List<String>> paramOptions = core.getParamOptions(true);
//        Map<String, Boolean> paramsMultiple = core.getAreDataParamsMultiple(true);
        ParamsPanel paramsPanel = new ParamsPanel(this, paramOptions, paramsMultiple);
        fullParamsPanel.add(paramsPanel);
        add(fullParamsPanel);

        JButton getDataButton = new JButton("Get data");
        add(getDataButton);
    }

    public void onPluginRegistered(Plugin plugin) {
        browsePanel.onPluginRegistered(plugin);
    }

    public void onDateChosen(Calendar date, boolean isStart){
        if (isStart) startDate = date;
        else endDate = date;
    }

    public JButton getDataButton(){
        JButton b = new JButton("Get data");
        b.addActionListener(e -> {
            core.getData();
        });
        return b;
    }

}
