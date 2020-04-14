package edu.cmu.cs.cs214.hw5.plugins.display;

import edu.cmu.cs.cs214.hw5.core.DisplayPlugin;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class LineChartDisplayPlugin implements DisplayPlugin {
    /** {@inheritDoc} */
    @Override
    public String getChartTypeName() {
        return "Line Chart";
    }

    /** {@inheritDoc} */
    @Override
    public Boolean isTimeSeriesChart() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public JPanel getEmptyChart() {
        return new XChartPanel<>(getEmptyChartHelper("Untitled Chart"));
    }

    /** {@inheritDoc} */
    @Override
    public JPanel getTimeSeriesChart(List<TimeSeries> tsList) {
        return new XChartPanel<>(getChartHelper(tsList));
    }

    /** {@inheritDoc} */
    @Override
    public JPanel getTimePointChart(List<TimePoint> tpList) {
        return null;
    }

    private XYChart getEmptyChartHelper(String title) {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName())
                .xAxisTitle("Time").yAxisTitle(title).build();

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

    private XYChart getChartHelper(List<TimeSeries> tsList) {

        // Create Chart
        XYChart chart = getEmptyChartHelper(tsList.get(0).getName());

        // Series
        for (TimeSeries ts : tsList) {
            Set<LocalDate> timeSpan = ts.getTimeSpan();
            List<Date> xTimes = new ArrayList<>();
            List<Double> yData = new ArrayList<>();
            for (LocalDate time : timeSpan) {
                Date date = Date.from(time.atStartOfDay(ZoneId.systemDefault()).toInstant());
                xTimes.add(date);
                yData.add(ts.getValue(time));
            }

            chart.addSeries(ts.getName(), xTimes, yData);
        }

        return chart;
    }
}
