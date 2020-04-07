package edu.cmu.cs.cs214.team24.framework.core;

import javax.swing.JPanel;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FrameworkImpl implements Framework {
    private DataPlugin currentDataPlugin;
    private DisplayPlugin currentDisplayPlugin;
    private Map<String, List<String>> dataParamOptions;
    private Map<String, List<String>> displayParamOptions;
    private Map<String, Boolean> isDataParamsMultiple;
    private Map<String, Boolean> isDisplayParamsMultiple;
    private DataSet dataset;
    private GameChangeListener gameChangeListener;

    public FrameworkImpl() {
    }

    @Override
    public void registerPlugin(Plugin plugin) {
        plugin.onRegister(this);
        notifyPluginRegistered(plugin);
    }

    @Override
    public void setCurrentDataPlugin(Plugin plugin) {
        currentDataPlugin = (DataPlugin) plugin;
    }

    @Override
    public void setCurrentDisplayPlugin(Plugin plugin) {
        currentDisplayPlugin = (DisplayPlugin) plugin;
    }


    @Override
    public Map<String, List<String>> getParamOptions(boolean isDataPlugin) {
        if (isDataPlugin) {
            return currentDataPlugin.getParamOptions();
        } else {
            return currentDisplayPlugin.getParamOptions();
        }
    }

    @Override
    public boolean setPluginParameters(boolean isDataPlugin, Map<String, List<String>> params, Date startDate, Date endDate) {
        Plugin plugin;
        if (isDataPlugin) {
            currentDataPlugin.setTimePeriod(startDate, endDate);
            plugin = currentDataPlugin;
        } else {
            plugin = currentDisplayPlugin;
        }

        for (Map.Entry<String, List<String>> e : params.entrySet()) {
            for (String s : e.getValue()) {
                if (!plugin.addParam(e.getKey(), s)) {
                    System.out.println("Set param failed! Param = " + e.getKey() + ", value = " + s);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean getData() {
        DataSet data = currentDataPlugin.getData();
        if (data == null) {
            return false;
        } else {
            dataset = data;
            return true;
        }
    }

    @Override
    public JPanel processAndDisplay() {
        return null;
    }

    @Override
    public void setGameChangeListener(GameChangeListener listener) {
        gameChangeListener = listener;
    }

    private void notifyPluginRegistered(Plugin plugin) {
        gameChangeListener.onPluginRegistered(plugin);
    }
}