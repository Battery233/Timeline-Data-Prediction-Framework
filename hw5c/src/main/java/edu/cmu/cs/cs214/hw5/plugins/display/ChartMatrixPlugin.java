package edu.cmu.cs.cs214.hw5.plugins.display;

import edu.cmu.cs.cs214.hw5.core.DisplayPlugin;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class ChartMatrixPlugin implements DisplayPlugin {
    @Override
    public String getChartTypeName() {
        return "Chart Matrix";
    }

    @Override
    public Boolean isTimeSeriesChart() {
        return true;
    }

    @Override
    public JPanel getEmptyChart() {
        return new XChartPanel<>(getEmptyChartHelper());
    }

    @Override
    public JPanel getTimeSeriesChart(List<TimeSeries> timeSeriesList) {
        return getChartMatrixHelper(timeSeriesList);
    }

    @Override
    public JPanel getTimePointChart(List<TimePoint> timePointList) {
        return null;
    }

    private XYChart getEmptyChartHelper(){
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName())
                .xAxisTitle("Time").yAxisTitle("Untitled Chart").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        chart.getStyler().setXAxisDecimalPattern("####.##");
        chart.getStyler().setYAxisDecimalPattern("#,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        return chart;
    }

    private JPanel getChartMatrixHelper(List<TimeSeries> tsList){
        JPanel matrix = new JPanel();
        int size = tsList.size();
        int cols = 2;
        if (size == 1) cols = 1;
        matrix.setLayout(new GridLayout(size/2, cols));
        for (TimeSeries ts: tsList){
            matrix.add(getChartHelper(ts));
        }
        return matrix;
    }

    private JPanel getChartHelper(TimeSeries ts){
        XYChart chart = getEmptyChartHelper();
        Set<LocalDate> timeSpan = ts.getTimeSpan();
        List<Date> xTimes = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        for (LocalDate time : timeSpan) {
            Date date = Date.from(time.atStartOfDay(ZoneId.systemDefault()).toInstant());
            xTimes.add(date);
            yData.add(ts.getValue(time));
        }
        chart.addSeries(ts.getName(), xTimes, yData);
        return new XChartPanel<>(chart);
    }

}
