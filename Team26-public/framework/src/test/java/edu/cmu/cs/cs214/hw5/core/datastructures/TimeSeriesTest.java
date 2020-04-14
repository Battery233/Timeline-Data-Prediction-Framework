package edu.cmu.cs.cs214.hw5.core.datastructures;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeSeriesTest {
    TimeSeries ts;
    TimeSeries ts2;
    LocalDate d1 = LocalDate.of(2010, 1, 1);
    LocalDate d2 = LocalDate.of(2010, 1, 3);
    LocalDate d3 = LocalDate.of(2010, 4, 1);
    LocalDate d4 = LocalDate.of(2011, 1, 1);
    LocalDate d5 = LocalDate.of(2012, 3, 20);

    @Before
    public void setUp() {
        ts = new TimeSeries("Test Time Series");
        ts2 = new TimeSeries("Test Time Series 2");

        ts.insert(d1, 10);
        ts.insert(d2, 12);
        ts.insert(d3, 20);
        ts.insert(d4, 200);

        ts2.insert(d1, 100);
        ts2.insert(d3, 100);
        ts2.insert(d5, 9);
    }

    @Test
    public void testGrowthRate() {
        TimeSeries growthRate = TimeSeries.growthRate(ts, ChronoUnit.DAYS);
        assertEquals("{2010-01-03=1.0, 2010-04-01=0.09090909090909091, 2011-01-01=0.6545454545454545}",
                growthRate.getStringRepresentation());
    }

    @Test
    public void testSimpleMovingAverage() {
        TimeSeries simpleMA = TimeSeries.simpleMovingAverage(ts, 2);
        assertEquals("{2010-01-03=11.0, 2010-04-01=16.0, 2011-01-01=110.0}",
                simpleMA.getStringRepresentation());
    }

    @Test
    public void testOrder() {
        ts.insert(LocalDate.of(1999, 10, 1), 1);
        ts.insert(LocalDate.of(2010, 3, 13), 15);
//        System.out.println(ts.getStringRepresentation());
        assertEquals("{1999-10-01=1.0, 2010-01-01=10.0, 2010-01-03=12.0, 2010-03-13=15.0, 2010-04-01=20.0, 2011-01-01=200.0}",
                ts.getStringRepresentation());
    }

    @Test
    public void testGetValue() {
        assertTrue(10 == ts.getValue(d1));
    }

    @Test
    public void testGetTimeSpan() {
        assertEquals("[2010-01-01, 2010-01-03, 2010-04-01, 2011-01-01]",
                ts.getTimeSpan().toString());
    }

    // invalidated due to revision for Best Framework
//    @Test
//    public void testPlus() {
//        assertEquals("{2010-01-01=110.0, 2010-04-01=120.0}",
//                TimeSeries.plus(ts, ts2).getStringRepresentation());
//    }
//
//    @Test
//    public void testMinus() {
//        assertEquals("{2010-01-01=-90.0, 2010-04-01=-80.0}",
//                TimeSeries.minus(ts, ts2).getStringRepresentation());
//    }
//
//    @Test
//    public void testMutiply() {
//        assertEquals("{2010-01-01=1000.0, 2010-04-01=2000.0}",
//                TimeSeries.multiply(ts, ts2).getStringRepresentation());
//    }
//
//    @Test
//    public void testDivide() {
//        assertEquals("{2010-01-01=0.1, 2010-04-01=0.2}",
//                TimeSeries.dividedBy(ts, ts2).getStringRepresentation());
//    }

    @Test
    public void testGetOverlapTimeSpan() {
        List<TimeSeries> testList = new ArrayList<>();
        testList.add(ts);
        testList.add(ts2);
        assertEquals("[2010-01-01, 2010-04-01]",
                TimeSeries.getOverlapTime(testList).toString());
    }

//    @Test
//    public void testIterator() {
//        String result = "";
//        while (ts.iterator().hasNext()) {
//            result += ts.iterator().next().toString();
//        }
//        assertEquals("", result);
//    }

//    @Test
//    public void testDateParse() {
//        String text = "2020/11/01";
//        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy[[-][.][/]MM[[-][.][/]dd]]")
//                .parseDefaulting(ChronoField.MONTH_OF_YEAR,1)
//                .parseDefaulting(ChronoField.DAY_OF_MONTH,1)
//                .toFormatter();
//        LocalDate parsedDate = LocalDate.parse(text, formatter);
//        System.out.println(parsedDate);
//    }

}
