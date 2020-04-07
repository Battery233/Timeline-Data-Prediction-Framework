package edu.cmu.cs.cs214.team24.framework.gui;

import edu.cmu.cs.cs214.team24.framework.core.DisplayPlugin;
import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;
import edu.cmu.cs.cs214.team24.framework.gui.plugin.BrowsePanel;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

public class DisplayPluginPanel extends PluginPanel {

    public DisplayPluginPanel(Framework framework) {
        super(framework, false);
        addStatusLabel();
        addBrowsePanel();
        addParamsPanel();
        addButton();
    }

    // TODO: will be deleted later
    @Override
    public void onPluginChanged(Plugin plugin){
        core.setCurrentDisplayPlugin(plugin);
//        Map<String, List<String>> paramOptions = core.getParamOptions(false);
        /** added for test */
        Map<String, List<String>> paramOptions = new HashMap<>();
        String[] bases = {"CAD", "USD", "CNY"};
        paramOptions.put("t-test params", Arrays.asList(bases));
        Map<String, Boolean> paramsMultiple = new HashMap<>();
        paramsMultiple.put("t-test params", true);
        /** added for test */
//        Map<String, Boolean> paramsMultiple = core.getAreDataParamsMultiple(true);
        paramsPanel.refresh(paramOptions, paramsMultiple);
    }

}
