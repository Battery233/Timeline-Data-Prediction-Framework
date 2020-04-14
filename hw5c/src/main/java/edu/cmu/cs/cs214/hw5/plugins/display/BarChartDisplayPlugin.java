package edu.cmu.cs.cs214.hw5.plugins.display;

import edu.cmu.cs.cs214.hw5.core.DisplayPlugin;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class BarChartDisplayPlugin implements DisplayPlugin {
    /** {@inheritDoc} */
    @Override
    public String getChartTypeName() {
        return "Bar Chart";
    }

    /** {@inheritDoc} */
    @Override
    public Boolean isTimeSeriesChart() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public JPanel getEmptyChart() {
        return new XChartPanel<>(getEmptyChartHelper());
    }

    /** {@inheritDoc} */
    @Override
    public JPanel getTimeSeriesChart(List<TimeSeries> tsList) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public JPanel getTimePointChart(List<TimePoint> tpList) {
        return new XChartPanel<>(getChartHelper(tpList));
    }

    private XYChart getEmptyChartHelper() {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName())
                .xAxisTitle("Categories").yAxisTitle("Value").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        return chart;
    }

    private CategoryChart getEmptyBarChartHelper() {
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(getClass().getSimpleName())
                .xAxisTitle("Categories").yAxisTitle("Value").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setAvailableSpaceFill(.95);

        return chart;
    }

    private CategoryChart getChartHelper(List<TimePoint> tpList) {

        // Create Chart
        CategoryChart chart = getEmptyBarChartHelper();

        for (TimePoint tp : tpList) {
            List<String> names = new ArrayList<>();
            List<Double> values = new ArrayList<>();
            String name = tp.getName();
            double value = tp.getValue();
            names.add(" ");
            values.add(value);

            chart.addSeries(name, names, values);
        }

        return chart;
    }
}