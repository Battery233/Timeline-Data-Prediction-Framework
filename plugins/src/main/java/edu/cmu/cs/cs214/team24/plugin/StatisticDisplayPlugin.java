package edu.cmu.cs.cs214.team24.plugin;

import edu.cmu.cs.cs214.team24.framework.core.DisplayDataSet;
import edu.cmu.cs.cs214.team24.framework.core.DisplayPlugin;

import javax.swing.*;
import java.util.*;

public class StatisticDisplayPlugin implements DisplayPlugin {

    private String option1;
    private String option2;
    private final DisplayDataSet data;
    private final Set<String> options;

    public StatisticDisplayPlugin(DisplayDataSet data) {
        this.data = data;
        options = data.getOriginalData().getData().keySet();
    }

    @Override
    public JPanel display() {
        return null;
    }

    @Override
    public String name() {
        return "StatisticDisplay";
    }

    @Override
    public boolean isDataPlugin() {
        return false;
    }

    @Override
    public boolean isDisplayPlugin() {
        return true;
    }

    @Override
    public Map<String, List<String>> getParamOptions() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> optionsList = new ArrayList<>(options);
        map.put("option1", optionsList);
        map.put("option2", optionsList);
        return map;
    }

    @Override
    public Map<String, Boolean> isParamsMultiple() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("option1", false);
        map.put("option2", false);
        return map;
    }

    @Override
    public boolean addParam(String param, String option) {
        switch (param) {
            case "option1":
                option1 = option;
                return true;
            case "option2":
                option2 = option;
                return true;
            default:
                return false;
        }
    }



}
