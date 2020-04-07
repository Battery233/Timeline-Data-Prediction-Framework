package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;

import java.util.*;

public class DisplayPluginPanel extends PluginPanel {

    public DisplayPluginPanel(MainPanel parent, Framework framework) {
        super(parent, framework, false);
        addStatusPanel();
        addBrowsePanel();
    }

    @Override
    public void enableBrowsePanel(){
        addParamsPanel();
        addButtonPanel();
        browsePanel.enableSelection();
    }


}
