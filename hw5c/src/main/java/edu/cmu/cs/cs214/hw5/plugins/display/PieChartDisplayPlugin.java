package edu.cmu.cs.cs214.hw5.plugins.display;

import edu.cmu.cs.cs214.hw5.core.DisplayPlugin;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PieChartDisplayPlugin implements DisplayPlugin {

    private final int COLORINTERVAL = 256;
    @Override
    public String getChartTypeName() {
        return "Pie Chart";
    }

    @Override
    public Boolean isTimeSeriesChart() {
        return false;
    }

    @Override
    public JPanel getEmptyChart() {
         return new XChartPanel<>(getEmptyChartHelper());
    }

    @Override
    public JPanel getTimeSeriesChart(List<TimeSeries> tsList) {
        return null;
    }

    @Override
    public JPanel getTimePointChart(List<TimePoint> tpList) {
        return new XChartPanel<>(getChartHelper(tpList));
    }

    private PieChart getEmptyChartHelper() {
        PieChart chart = new PieChartBuilder().width(800).height(800).title(getClass().getSimpleName()).build();
        return chart;
    }

    private PieChart getChartHelper(List<TimePoint> tpList) {
        PieChart chart = getEmptyChartHelper();

        Color[] sliceColors = new Color[tpList.size()];
        float interval = COLORINTERVAL / tpList.size();
        int i = 0;
        float j = 0;
        while (i < tpList.size() && j < 360) {
            sliceColors[i] = Color.getHSBColor(j / COLORINTERVAL, 1.0f, 1.0f);
            i++;
            j += interval;
        }

        chart.getStyler().setSeriesColors(sliceColors);

        for (TimePoint tp : tpList) {
            chart.addSeries(tp.getName(), tp.getValue());
        }

        return chart;
    }
}
