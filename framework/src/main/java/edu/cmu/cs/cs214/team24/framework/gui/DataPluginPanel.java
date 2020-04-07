package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;

import javax.swing.*;
import java.awt.*;

public class DataPluginPanel extends JPanel {

    private Framework core;
    private JLabel statusLabel = new JLabel("Data not ready, please get data.");
    private BrowsePanel browsePanel;

    public DataPluginPanel(Framework framework){
        core = framework;

        setLayout(new GridLayout(2, 1));
        add(statusLabel);
        browsePanel = new BrowsePanel();
        add(browsePanel);
    }

    public void onPluginRegistered(Plugin plugin) {
        browsePanel.onPluginRegistered(plugin);
    }

}
