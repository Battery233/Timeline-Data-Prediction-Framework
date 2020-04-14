package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;
import java.util.List;

public class DummyDisplayPlugin implements DisplayPlugin {
    boolean getEmptyChartCalled;
    boolean plotTimeSeriesCalled;
    boolean plotTimePointsCalled;

    @Override
    public String getChartTypeName() {
        return null;
    }

    @Override
    public Boolean isTimeSeriesChart() {
        return true;
    }

    @Override
    public JPanel getEmptyChart() {
        getEmptyChartCalled = true;
        return null;
    }

    @Override
    public JPanel getTimeSeriesChart(List<TimeSeries> timeSeriesList) {
        plotTimeSeriesCalled = true;
        return null;
    }

    @Override
    public JPanel getTimePointChart(List<TimePoint> timePointList) {
        plotTimePointsCalled = true;
        return null;
    }
}
