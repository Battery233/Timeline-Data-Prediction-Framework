package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;

import java.util.*;

public class DisplayPluginPanel extends PluginPanel {

    private boolean browseEnabled = false;

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
    }

    @Override
    public void disableBrowsePanel(){
        browsePanel.disableSelection();
        browseEnabled = false;
        clearParams();
    }

    @Override
    public void onPluginChanged(Plugin plugin){
        core.setCurrentDisplayPlugin(plugin);
        if (!browseEnabled){
            core.setDisplayPluginOptions();
            browseEnabled = true;
        }
        retrieveParams();
        refreshParams();
    }

    private void clearParams(){
        paramOptions = new HashMap<>();
        paramsMultiple = new HashMap<>();
        refreshParams();
    }

}
