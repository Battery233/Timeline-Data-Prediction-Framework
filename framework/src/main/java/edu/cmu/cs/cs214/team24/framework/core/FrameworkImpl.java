package edu.cmu.cs.cs214.team24.framework.core;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import javax.swing.JPanel;
import java.util.*;

public class FrameworkImpl implements Framework {
    private DataPlugin currentDataPlugin;
    private DisplayPlugin currentDisplayPlugin;
    private Map<String, List<String>> dataParamOptions;
    private Map<String, List<String>> displayParamOptions;
    private DataSet dataset;
    private StatusChangeListener statusChangeListener;

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
    public Map<String, Boolean> getAreDataParamsMultiple(boolean isDataPlugin) {
        if (isDataPlugin) return currentDataPlugin.areParamsMultiple();
        else return currentDisplayPlugin.areParamsMultiple();
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
    public void setStatusChangeListener(StatusChangeListener listener) {
        statusChangeListener = listener;
    }

    public Map<String, List<Double>> predict(DataSet data, int n, int degree) {
        WeightedObservedPoints points = new WeightedObservedPoints();
        Map<String, double[]> map = data.getData();
        Map<String, List<Double>> res = new HashMap<>();
        for (Map.Entry<String, double[]> entry : map.entrySet()) {
            String key = entry.getKey();
            double[] cur = entry.getValue();
            int size = cur.length;
            List<Double> list = new ArrayList<>();
            for (int i = 0; i < cur.length; i++) {
                list.add(cur[i]);
                points.add(i + 1, cur[i]);
            }
            PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
            double[] result = fitter.fit(points.toList());
            for (int i = 0; i < n; i++) {
                double predictions = calculate(result, size + i + 1);
                list.add(predictions);
            }
            res.put(key, list);
        }
        return res;
    }

    private double calculate(double[] coefficients, int x) {
        double res = coefficients[0];
        for (int i = 1; i < coefficients.length; i++) {
            res += coefficients[i] * Math.pow(x, i);
        }
        return res;
    }

    private void notifyPluginRegistered(Plugin plugin) {
        statusChangeListener.onPluginRegistered(plugin);
    }
}