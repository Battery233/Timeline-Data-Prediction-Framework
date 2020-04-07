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

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class PluginPanel extends JPanel {

    protected MainPanel parent;
    protected Framework core;
    protected BrowsePanel browsePanel;
    protected ParamsPanel paramsPanel;
    protected JLabel statusLabel;
    protected boolean isDataPlugin;
    protected Date startDate, endDate;
    protected Map<String, List<String>> paramOptions = new HashMap<>();
    protected Map<String, Boolean> paramsMultiple = new HashMap<>();

    public PluginPanel(MainPanel parent, Framework framework, boolean isDataPlugin) {
        this.parent = parent;
        core = framework;
        this.isDataPlugin = isDataPlugin;
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
    }

    protected void addStatusPanel(){
        String statusText = "Data not ready, please configure data parameters and get data.";
        if (!isDataPlugin) statusText = "Please configure display parameters and display.";
        statusLabel = new JLabel(statusText);
        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);
        add(statusPanel);
    }

    protected void addBrowsePanel(){
        browsePanel = new BrowsePanel(this, isDataPlugin);
        add(browsePanel);
    }

    protected void addParamsPanel(){
        paramsPanel = new ParamsPanel();
        JScrollPane scroll = new JScrollPane(paramsPanel);
        scroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        paramsPanel.setScrollPane(scroll);
        add(scroll);
    }

    protected void addButtonPanel(){
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
                        parent.onGetDataSuccess();
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
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(b);
        add(buttonPanel);
    }

    public void onPluginRegistered(Plugin plugin) {
        browsePanel.onPluginRegistered(plugin);
    }

    public void onPluginChanged(Plugin plugin){
    }

    public void enableBrowsePanel(){
    }

    public void disableBrowsePanel(){
    }

    protected void retrieveParams(){
        paramOptions = core.getParamOptions(isDataPlugin);
        paramsMultiple = core.getAreDataParamsMultiple(isDataPlugin);
    }

    protected void refreshParams(){
        paramsPanel.refresh(paramOptions, paramsMultiple);
        revalidate();
        repaint();
    }

}
