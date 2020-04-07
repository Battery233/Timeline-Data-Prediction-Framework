package edu.cmu.cs.cs214.team24.plugin;

import edu.cmu.cs.cs214.team24.framework.core.DataSet;
import edu.cmu.cs.cs214.team24.framework.core.DisplayDataSet;
import edu.cmu.cs.cs214.team24.framework.core.DisplayPlugin;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import java.util.*;

public class LineChartDisplayPlugin implements DisplayPlugin {

    private Map<String, List<String>> toDisplay;
    private DisplayDataSet data;
    private Set<String> options;

    public LineChartDisplayPlugin() {
        toDisplay = new HashMap<>();
    }

    @Override
    public void setDataSet(DataSet metaData){
        this.options = metaData.getData().keySet();
    }

    @Override
    public void setDisplayDataSet(DisplayDataSet data) {
        this.data = data;
    }

    @Override
    public JPanel display() {
        JPanel panel = new JPanel();
        JFXPanel fxPanel = getChart();
        panel.add(fxPanel);
        return panel;
    }

    public JFXPanel getChart() {
        JFXPanel jfxPanel = new JFXPanel();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        final LineChart<String,Number> lineChart = new LineChart<>(xAxis,yAxis);
        List<String> list = toDisplay.get("toAdd");
        for (String s : list) {
            XYChart.Series series = new XYChart.Series();
            series.setName(s);
            double[] rowData = data.getOriginalData().getData().get(s);
            Date[] dates = data.getOriginalData().getTimeRange();
            for (int i = 0; i < dates.length; i++) {
                series.getData().add(new XYChart.Data(dates[i].toString(), rowData[i]));
            }
            series.getData().add(new XYChart.Data(data.getPredictionDate().toString(), data.getPredictionValue()));
            lineChart.getData().add(series);
        }

        Scene scene  = new Scene(lineChart,800,600);
        jfxPanel.setScene(scene);
        return jfxPanel;
    }

    @Override
    public String name() {
        return "Line Chart Display Plugin";
    }

    @Override
    public boolean isDataPlugin() {
        return false;
    }

    @Override
    public Map<String, List<String>> getParamOptions() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> optionsList = new ArrayList<>(options);
        map.put("toAdd", optionsList);
        return map;
    }

    @Override
    public Map<String, Boolean> areParamsMultiple() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("toAdd", true);
        return map;
    }

    @Override
    public boolean addParam(String param, String option) {
        if (!toDisplay.containsKey(param)) {
            List<String> list = new ArrayList<>();
            toDisplay.put(param, list);
        }
        List<String> list = toDisplay.get(param);
        list.add(option);
        toDisplay.put(param, list);
        return true;
    }
}
