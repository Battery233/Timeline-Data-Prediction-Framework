package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.StatusChangeListener;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel implements StatusChangeListener {

    private Framework core;
    private DataPluginPanel dataPluginPanel;
    private DisplayPluginPanel displayPluginPanel;

    public MainPanel(Framework framework) {
        core = framework;
        dataPluginPanel = new DataPluginPanel(core);
        displayPluginPanel = new DisplayPluginPanel(core);

        setLayout(new GridLayout(1, 1));
        add(dataPluginPanel);
    }

    @Override
    public void onPluginRegistered(Plugin plugin) {
        if (plugin.isDataPlugin()) dataPluginPanel.onPluginRegistered(plugin);
        else displayPluginPanel.onPluginRegistered(plugin);
    }
}
