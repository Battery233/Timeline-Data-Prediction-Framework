package edu.cmu.cs.cs214.team24.framework.core;

import javax.swing.JPanel;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FrameworkImpl implements Framework {
    private Plugin currentDataPlugin;
    private Plugin currentDisplayPlugin;
    private Map<String, List<String>> dataParamOptions;
    private Map<String, List<String>> displayParamOptions;
    private Map<String, Boolean> isDataParamsMultiple;
    private Map<String, Boolean> isDisplayParamsMultiple;
    private GameChangeListener gameChangeListener;

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
    public void setGameChangeListener(GameChangeListener listener) {
        gameChangeListener = listener;
    }

    private void notifyPluginRegistered(Plugin plugin) {
        gameChangeListener.onPluginRegistered(plugin);
    }
}