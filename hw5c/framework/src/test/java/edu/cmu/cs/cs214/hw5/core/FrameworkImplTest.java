package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FrameworkImplTest {
    private DummyDisplayPlugin displayPlugin;
    private DataPlugin dataPlugin;
    private FrameworkImpl framework;
    private DummyChangeListener gui;
    private DataSet ds;
    private List<TimeSeries> emptyTimeSeriesList;
    private List<TimeSeries> timeSeriesList;
    private List<TimePoint> emptyTimePointsList;
    private List<TimePoint> timePointList;
    private LocalDate d1;
    private double v1;

    @Before
    public void setUp() {
        displayPlugin = new DummyDisplayPlugin();
        dataPlugin = new DummyDataPlugin();
        gui = new DummyChangeListener();
        framework = new FrameworkImpl();
        framework.registerDataPlugin(dataPlugin);
        framework.registerDisplayPlugin(displayPlugin);
        framework.setChangeListener(gui);
        ds = DataSet.EMPTY_DATASET;
        emptyTimeSeriesList = new ArrayList<>();
        timeSeriesList = Collections.singletonList(new TimeSeries("test ts"));
        emptyTimePointsList = new ArrayList<>();
        d1 = LocalDate.of(2012, 12, 31);
        v1 = 17214;
        timePointList = Collections.singletonList(new TimePoint(d1, v1, "test tp"));
        framework.newChart(displayPlugin);
    }

    @Test
    public void testRegisterDataPlugin() {
        assertEquals(dataPlugin, framework.getDataPluginList().get(0));
    }

    @Test
    public void testRegisterDisplayPlugin() {
        assertEquals(displayPlugin, framework.getDisplayPluginList().get(0));
    }

    @Test
    public void testSetChangeListener() {
        assertEquals(gui, framework.changeListener);
    }

    @Test
    public void testNewChart() {
        assertEquals(displayPlugin, framework.currentDisplayPlugin);
        assertTrue(gui.onNewChartCalled);
    }

    @Test
    public void testAddDataSet() {
        framework.addDataSet(dataPlugin, "test");
        assertTrue(gui.onDataSetAddedCalled);
    }

    @Test
    public void testAddDataSet2() {
        framework.addDataSet(ds);
        assertTrue(gui.onDataSetAddedCalled);
    }

    @Test
    public void testPlotEmptyTimeSeries() {
        framework.plotTimeSeries(emptyTimeSeriesList);
        assertTrue(displayPlugin.getEmptyChartCalled);
        assertTrue(gui.onNewChartCalled);
    }

    @Test
    public void testPlotTimeSeries() {
        framework.plotTimeSeries(timeSeriesList);
        assertTrue(displayPlugin.plotTimeSeriesCalled);
        assertTrue(gui.onNewChartCalled);
    }

    @Test
    public void testPlotEmptyTimePoints() {
        framework.plotTimePoint(emptyTimePointsList);
        assertTrue(displayPlugin.getEmptyChartCalled);
        assertTrue(gui.onNewChartCalled);
    }

    @Test
    public void testPlotTimePoints() {
        framework.plotTimePoint(timePointList);
        assertTrue(displayPlugin.plotTimePointsCalled);
        assertTrue(gui.onNewChartCalled);
    }

    @Test
    public void testGetDisplayedPluginList() {
        assertEquals(displayPlugin, framework.getDisplayPluginList().get(0));
        assertEquals(1, framework.getDisplayPluginList().size());
    }

    @Test
    public void testGetDataPluginList() {
        assertEquals(dataPlugin, framework.getDataPluginList().get(0));
        assertEquals(1, framework.getDataPluginList().size());
    }


}

