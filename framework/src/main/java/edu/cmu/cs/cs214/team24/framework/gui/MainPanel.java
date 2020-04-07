package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.StatusChangeListener;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel implements StatusChangeListener {

    private Framework core;
    private PluginPanel dataPluginPanel, displayPluginPanel;
    private JPanel display = new JPanel();
    private JPanel pluginPanel = new JPanel();

    public MainPanel(Framework framework) {
        core = framework;
        dataPluginPanel = new DataPluginPanel(this, core);
        displayPluginPanel = new DisplayPluginPanel(this, core);

        pluginPanel.setLayout(new BoxLayout(pluginPanel, BoxLayout.PAGE_AXIS));
        getTitlePanel(true);
        pluginPanel.add(dataPluginPanel);
        getTitlePanel(false);
        pluginPanel.add(displayPluginPanel);

        setLayout(new BorderLayout());
        add(pluginPanel, BorderLayout.EAST);
        add(display, BorderLayout.CENTER);
    }

    private void getTitlePanel(boolean isDataPlugin){
        JPanel title = new JPanel();
        String titleText = "Data Plugin Area";
        if (!isDataPlugin) titleText = "Display Plugin Area";
        JLabel label = new JLabel(titleText);
        label.setFont(new Font("title", Font.BOLD, 18));
        title.add(label);
        pluginPanel.add(title);
    }

    @Override
    public void onPluginRegistered(Plugin plugin) {
        if (plugin.isDataPlugin()) dataPluginPanel.onPluginRegistered(plugin);
        else displayPluginPanel.onPluginRegistered(plugin);
    }

    public void onGetDataSuccess(){
        displayPluginPanel.enableBrowsePanel();
    }

    public void onDisplayChanged(JPanel newDisplay){
        display.removeAll();
        display = newDisplay;
        display.revalidate();
        display.repaint();
    }
}
