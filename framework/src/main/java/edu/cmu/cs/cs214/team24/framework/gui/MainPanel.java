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

    public MainPanel(Framework framework) {
        core = framework;
        dataPluginPanel = new DataPluginPanel(this, core);
        displayPluginPanel = new DisplayPluginPanel(this, core);

        JPanel pluginPanel = new JPanel();
        pluginPanel.setLayout(new BoxLayout(pluginPanel, BoxLayout.PAGE_AXIS));
        pluginPanel.add(dataPluginPanel);
        pluginPanel.add(displayPluginPanel);

        setLayout(new BorderLayout());
        add(pluginPanel, BorderLayout.EAST);
        add(display, BorderLayout.CENTER);
    }

    @Override
    public void onPluginRegistered(Plugin plugin) {
        if (plugin.isDataPlugin()) dataPluginPanel.onPluginRegistered(plugin);
        else displayPluginPanel.onPluginRegistered(plugin);
    }

    public void onDisplayChanged(JPanel newDisplay){
        display.removeAll();
        display = newDisplay;
        display.revalidate();
        display.repaint();
    }
}
