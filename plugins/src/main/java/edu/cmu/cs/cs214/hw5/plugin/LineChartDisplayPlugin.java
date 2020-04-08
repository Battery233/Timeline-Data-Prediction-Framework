package edu.cmu.cs.cs214.hw5.plugin;

import edu.cmu.cs.cs214.hw5.framework.core.DataSet;
import edu.cmu.cs.cs214.hw5.framework.core.DisplayDataSet;
import edu.cmu.cs.cs214.hw5.framework.core.DisplayPlugin;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LineChartDisplayPlugin implements DisplayPlugin {

    private Map<String, List<String>> toDisplay;
    private DisplayDataSet data;
    private Set<String> options;
    private JFrame frame;

    public LineChartDisplayPlugin() {
        toDisplay = new HashMap<>();
    }

    @Override
    public void setDataSet(DataSet metaData) {
        this.options = metaData.getData().keySet();
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
        JFrame newFrame = new JFrame("Line Chart");
        JFXPanel fxPanel = new JFXPanel();
        newFrame.add(fxPanel);
        newFrame.setPreferredSize(new Dimension(800, 800));
        newFrame.pack();
        newFrame.setVisible(true);
        frame = newFrame;

        Platform.runLater(() -> initFX(fxPanel));

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
        xAxis.setLabel("Day");
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        List<String> list = toDisplay.get("toAdd");
        for (String s : list) {
            XYChart.Series series = new XYChart.Series();
            series.setName(s);
            double[] rowData = data.getOriginalData().getData().get(s);
            Date[] dates = data.getOriginalData().getTimeRange();
            for (int i = 0; i < dates.length; i++) {
                series.getData().add(new XYChart.Data(dates[i].toString(), rowData[i]));
            }
            series.getData().add(new XYChart.Data(data.getPredictionDate().toString() + " (!!!This is the prediction value!!!)", data.getPredictionValue().get(s)));
            lineChart.getData().add(series);
        }

        return new Scene(lineChart, 800, 600);
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
        if (!param.equals("toAdd")) {
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
     *
     * @return true or false
     */
    public boolean isToDisplayEmpty() {
        return toDisplay.size() == 0;
    }
}
