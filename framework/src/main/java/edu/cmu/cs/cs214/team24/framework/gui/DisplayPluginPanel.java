package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;

import java.awt.*;
import java.util.*;

public class DisplayPluginPanel extends PluginPanel {

    public DisplayPluginPanel(MainPanel parent, Framework framework) {
        super(parent, framework, false);
        addStatusPanel();
        addBrowsePanel();
        addParamsPanel();
        addButtonPanel();
    }

    @Override
    public void enableBrowsePanel(){
        browsePanel.enableSelection();
        statusLabel.setText("Please choose a display plugin.");
    }

    @Override
    public void disableBrowsePanel(){
        browsePanel.disableSelection();
        clearParams();
        statusLabel.setText("Please get data before proceed to choose a display plugin.");
        statusLabel.setForeground(Color.red);
    }

    @Override
    public void onPluginChanged(Plugin plugin){
        core.setCurrentDisplayPlugin(plugin);
        core.setDisplayPluginOptions();
        retrieveParams();
        refreshParams();
        statusLabel.setText("Please configure display parameters and display. " +
                "All of the followings are single selection.");
        statusLabel.setForeground(Color.red);
    }

    private void clearParams(){
        paramOptions = new HashMap<>();
        paramsMultiple = new HashMap<>();
        refreshParams();
    }

}
