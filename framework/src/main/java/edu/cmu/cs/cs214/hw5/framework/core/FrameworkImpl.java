package edu.cmu.cs.cs214.hw5.framework.core;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import javax.swing.JPanel;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameworkImpl implements Framework {
    private static long SECONDS_PER_DAY = 86400000;
    private DataPlugin currentDataPlugin;
    private DisplayPlugin currentDisplayPlugin;
    private DataSet dataset;
    private StatusChangeListener statusChangeListener;

    public FrameworkImpl() {
    }

    @Override
    public void registerPlugin(Plugin plugin) {
        notifyPluginRegistered(plugin);
    }

    @Override
    public void setCurrentDataPlugin(Plugin plugin) {
        currentDataPlugin = (DataPlugin) plugin;
    }

    @Override
    public void setCurrentDisplayPlugin(Plugin plugin) {
        currentDisplayPlugin = (DisplayPlugin) plugin;
        currentDisplayPlugin.clearToDisplay();
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
    public void setDisplayPluginOptions() {
        currentDisplayPlugin.setDataSet(dataset);
    }

    @Override
    public JPanel processAndDisplay() {
        Map<String, Double> prediction = predict(dataset, 3);
        Date[] dates = dataset.getTimeRange();
        Date latestDate = dates[dates.length - 1];
        Date newDate = new Date(latestDate.getTime() + SECONDS_PER_DAY);

        DisplayDataSet displayDataSet = new DisplayDataSet(dataset, newDate, prediction);
        currentDisplayPlugin.setDisplayDataSet(displayDataSet);

        return currentDisplayPlugin.display();

    }

    @Override
    public void setStatusChangeListener(StatusChangeListener listener) {
        statusChangeListener = listener;
    }

    /**
     * Use the existing data to predict a value for the next time(day/ week/ month/ year). We use
     * PolynomialCurveFitter for the prediction.
     * @param data   the data set extracted from the data plugin
     * @param degree  the degree of the PolynomialCurveFitter.
     * @return   A map of the prediction result. The map key is the type of the prediction value and
     *           the map value is the prediction value which is in a Double format.
     */
    public Map<String, Double> predict(DataSet data, int degree) {
        WeightedObservedPoints points = new WeightedObservedPoints();
        Map<String, double[]> map = data.getData();
        Map<String, Double> res = new HashMap<>();
        for (Map.Entry<String, double[]> entry : map.entrySet()) {
            String key = entry.getKey();
            double[] cur = entry.getValue();
            int size = cur.length;
            for (int i = 0; i < cur.length; i++) {
                points.add(i + 1, cur[i]);
            }
            PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
            double[] result = fitter.fit(points.toList());
            double predictions = calculate(result, size + 1);
            res.put(key, predictions);
        }
        return res;
    }

    private double calculate(double[] coefficients, int x) {
        double res = coefficients[0];
        // Calculate the final prediction result based on coefficient.
        for (int i = 1; i < coefficients.length; i++) {
            res += coefficients[i] * Math.pow(x, i);
        }
        return res;
    }

    private void notifyPluginRegistered(Plugin plugin) {
        statusChangeListener.onPluginRegistered(plugin);
    }
}