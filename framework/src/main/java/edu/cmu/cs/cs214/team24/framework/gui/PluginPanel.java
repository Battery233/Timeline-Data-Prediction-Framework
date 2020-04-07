package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.BrowsePanel;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.ParamsPanel;

import javax.swing.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginPanel extends JPanel {

    private MainPanel parent;
    protected Framework core;
    protected BrowsePanel browsePanel;
    protected ParamsPanel paramsPanel;
    protected JLabel statusLabel;
    private boolean isDataPlugin;
    protected Date startDate, endDate;

    public PluginPanel(MainPanel parent, Framework framework, boolean isDataPlugin) {
        this.parent = parent;
        core = framework;
        this.isDataPlugin = isDataPlugin;
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
    }

    protected void addStatusLabel(){
        String statusText = "Data not ready, please get data.";
        if (!isDataPlugin) statusText = "Please configure display parameters if there is any.";
        statusLabel = new JLabel(statusText);
        add(statusLabel);
    }

    protected void addBrowsePanel(){
        browsePanel = new BrowsePanel(this, isDataPlugin);
        add(browsePanel);
    }

    protected void addParamsPanel(){
        paramsPanel = new ParamsPanel();
        add(paramsPanel);
    }

    protected void addButton(){
        String buttonText = "Get Data";
        if (!isDataPlugin) buttonText = "Display";
        JButton b = new JButton(buttonText);
        b.addActionListener(e -> {
            Map<String, List<String>> paramValues = paramsPanel.getParamsValues();
            boolean configSuccess = core.setPluginParameters(
                    isDataPlugin, paramValues, startDate, endDate);
            if (!configSuccess) {
                statusLabel.setText("Invalid configuration. Please re-check parameters.");
            }
            else {
                if (isDataPlugin) {
                    boolean getDataSuccess = core.getData();
                    if (getDataSuccess) {
                        statusLabel.setText("Data ready. Proceed to display data.");
                    } else {
                        statusLabel.setText("Get data failed. Please modify your configuration.");
                    }
                }
                else {
                    JPanel newDisplay = core.processAndDisplay();
                    parent.onDisplayChanged(newDisplay);
                }
            }
        });
        add(b);
    }

    public void onPluginRegistered(Plugin plugin) {
        browsePanel.onPluginRegistered(plugin);
    }

    public void onPluginChanged(Plugin plugin){
        if (isDataPlugin) core.setCurrentDataPlugin(plugin);
        else core.setCurrentDisplayPlugin(plugin);
        Map<String, List<String>> paramOptions = core.getParamOptions(isDataPlugin);
        Map<String, Boolean> paramsMultiple = core.getAreDataParamsMultiple(isDataPlugin);
        paramsPanel.refresh(paramOptions, paramsMultiple);
    }

}
