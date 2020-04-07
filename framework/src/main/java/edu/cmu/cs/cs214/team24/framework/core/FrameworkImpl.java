package edu.cmu.cs.cs214.team24.framework.core;

import javax.swing.JPanel;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameworkImpl implements Framework {
    private Plugin currentDataPlugin;
    private Plugin currentDisplayPlugin;
    private Map<String, List<String>> dataParamOptions;
    private Map<String, List<String>> displayParamOptions;
    private Map<String, Boolean> areDataParamsMultiple;
    private Map<String, Boolean> areDisplayParamsMultiple;
    private StatusChangeListener statusChangeListener;

    public FrameworkImpl() {
    }

    @Override
    public void registerPlugin(Plugin plugin){
        plugin.onRegister(this);
        notifyPluginRegistered(plugin);
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
    public Map<String, Boolean> getAreDataParamsMultiple(boolean isDataPlugin) {
        if (isDataPlugin) return new HashMap<>(areDataParamsMultiple);
        else return new HashMap<>(areDisplayParamsMultiple);
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

    @Override
    public void setStatusChangeListener(StatusChangeListener listener) {
        statusChangeListener = listener;
    }

    private void notifyPluginRegistered(Plugin plugin) {
        statusChangeListener.onPluginRegistered(plugin);
    }
}