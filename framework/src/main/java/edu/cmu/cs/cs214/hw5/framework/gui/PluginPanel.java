package edu.cmu.cs.cs214.hw5.framework.gui;

import edu.cmu.cs.cs214.hw5.framework.core.Framework;
import edu.cmu.cs.cs214.hw5.framework.core.Plugin;
import edu.cmu.cs.cs214.hw5.framework.gui.plugin.BrowsePanel;
import edu.cmu.cs.cs214.hw5.framework.gui.plugin.ParamsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;

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
        String statusText = "Please choose a data plugin.";
        if (!isDataPlugin) statusText = "Please get data before proceed to choose a display plugin.";
        statusLabel = new JLabel(statusText);
        statusLabel.setForeground(Color.red);
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
            for (List<String> val: paramValues.values()) {
                if (val.isEmpty()) {
                    statusLabel.setText("Please configure all parameters before proceed.");
                    return;
                }
            }
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
                        statusLabel.setForeground(Color.blue);
                        parent.onGetDataSuccess();
                    } else {
                        statusLabel.setText("Get data failed. Please modify your configuration.");
                    }
                }
                else {
                    JPanel newDisplay = core.processAndDisplay();
                    parent.onDisplayChanged(newDisplay);
                    statusLabel.setText("Display panel on the left.");
                    statusLabel.setForeground(Color.blue);
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
