package edu.cmu.cs.cs214.hw5.core.datastructures;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataSetTest {
    private DataSet ds;
    private String name;
    private List<TimeSeries> timeSeriesList;
    private TimeSeries ts;
    private List<TimePoint> timePointList;
    private LocalDate d1;
    private double v1;
    private TimePoint tp;

    @Before
    public void setUp() {
        name = "test";
        ts = new TimeSeries("test ts");
        d1 = LocalDate.of(2012, 12, 31);
        v1 = 17214;
        ts.insert(d1, v1);
        tp = new TimePoint(d1, v1, "test tp");
        ds = new DataSet(Collections.singletonList(ts),
                Collections.singletonList(tp),
                name);
    }

    @Test
    public void testGetTimeSeries() {
        assertEquals("[test ts]", ds.getTimeSeries().toString());
    }

    @Test
    public void testGetTimePoints() {
        assertEquals("[test tp]", ds.getTimePoints().toString());
    }
}
