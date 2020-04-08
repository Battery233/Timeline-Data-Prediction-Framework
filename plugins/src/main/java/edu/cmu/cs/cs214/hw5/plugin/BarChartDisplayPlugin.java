package edu.cmu.cs.cs214.hw5.plugin;

import edu.cmu.cs.cs214.hw5.framework.core.DataSet;
import edu.cmu.cs.cs214.hw5.framework.core.DisplayDataSet;
import edu.cmu.cs.cs214.hw5.framework.core.DisplayPlugin;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class BarChartDisplayPlugin implements DisplayPlugin {

    private DisplayDataSet data;
    private Map<String, List<String>> toDisplay;
    private Set<String> options;
    private JFrame frame;

    public BarChartDisplayPlugin() {
        toDisplay = new HashMap<>();
    }

    @Override
    public void setDataSet(DataSet metaData) {
        this.options = metaData.getData().keySet();
        //toDisplay = new HashMap<>();
    }

    @Override
    public void setDisplayDataSet(DisplayDataSet data) {
        this.data = data;
    }

    @Override
    public void clearToDisplay() {
        toDisplay = new HashMap<>();
    }

    @Override
    public JPanel display() {

        JPanel panel = new JPanel();
        if (frame != null) {
            frame.setVisible(false);
        }
        JFrame newFrame = new JFrame("Bar Chart");
        JFXPanel fxPanel = new JFXPanel();
        newFrame.add(fxPanel);
        newFrame.setPreferredSize(new Dimension(800, 800));
        newFrame.pack();
        newFrame.setVisible(true);

        frame = newFrame;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });

        JLabel jLabel = new JLabel("Due to the size of window, the result will be showed on a new extended window!");
        panel.add(jLabel);

        return panel;
    }

    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private Scene createScene() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Category");
        BarChart<String,Number> barChart = new BarChart<>(xAxis,yAxis);
        List<String> list = toDisplay.get("categories");
        for (String s : list) {
            XYChart.Series series = new XYChart.Series();
            series.setName(s);
            series.getData().add(new XYChart.Data(" !!!This is the prediction value!!!", data.getPredictionValue().get(s)));
            barChart.getData().add(series);
        }

        Scene scene  = new Scene(barChart,800,600);
        return scene;
    }

    @Override
    public String name() {
        return "Bar Chart Display Plugin";
    }

    @Override
    public boolean isDataPlugin() {
        return false;
    }

    @Override
    public Map<String, List<String>> getParamOptions() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> optionList = new ArrayList<>(options);
        map.put("categories", optionList);
        return map;
    }

    @Override
    public Map<String, Boolean> areParamsMultiple() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("categories", true);
        return map;
    }

    @Override
    public boolean addParam(String param, String option) {
        if (!param.equals("categories")) {
            return false;
        }
        if (!toDisplay.containsKey(param)) {
            List<String> list = new ArrayList<>();
            toDisplay.put(param, list);
        }
        List<String> list = toDisplay.get(param);
        list.add(option);
        toDisplay.put(param, list);
        return true;
    }

    /**
     * Check if the to display data structure is empty.
     * @return  true or false
     */
    public boolean isToDisplayEmpty() {
        return toDisplay.size() == 0;
    }
}
