package edu.cmu.cs.cs214.team24.framework.core;

import javax.swing.JPanel;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FrameworkImpl implements Framework {
    private final List<Plugin> dataPlugins;
    private final List<Plugin> displayPlugins;
    private Plugin currentDataPlugin;
    private Plugin currentDisplayPlugin;
    private Map<String, List<String>> dataParamOptions;
    private Map<String, List<String>> displayParamOptions;
    private Map<String, Boolean> isDataParamsMultiple;
    private Map<String, Boolean> isDisplayParamsMultiple;

    public FrameworkImpl() {
        dataPlugins = PluginLoader.registerDataPlugin();
        displayPlugins = PluginLoader.registerDisplayPlugin();
    }

    @Override
    public List<String> getDataPluginNames() {
        return null;
    }

    @Override
    public List<String> getDisplayPluginNames() {
        return null;
    }

    @Override
    public void setCurrentDataPlugin(Plugin plugin) {
        currentDataPlugin = plugin;
    }

    @Override
    public void setCurrentDisplayPlugin(Plugin plugin) {
        currentDisplayPlugin = plugin;
    }


    @Override
    public Map<String, List<String>> getParamOptions(boolean isDataPlugin) {
        return null;
    }

    @Override
    public boolean setPluginParameters(boolean isDataPlugin, Map<String, List<String>> params, Date startDate, Date endDate) {
        return false;
    }

    @Override
    public DataSet getData() {
        return null;
    }

    @Override
    public JPanel processAndDisplay() {
        return null;
    }
}