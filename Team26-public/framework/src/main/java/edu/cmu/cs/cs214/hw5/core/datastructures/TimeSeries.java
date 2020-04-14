package edu.cmu.cs.cs214.hw5.core.datastructures;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.*;

/**
 * The time series class that represents a time series.
 * Note that time is represented as LocalDate (YYYY-MM-DD)
 * and values in the time series is represented as double
 */
public class TimeSeries implements Iterable<Map.Entry<LocalDate, Double>> {
    private final String name;
    private final TreeMap<LocalDate, Double> time2double;

    /**
     * Constructs an empty time series with the given name
     *
     * @param name the name of the time series
     */
    public TimeSeries(String name) {
        this.name = name;
        this.time2double = new TreeMap<>();
    }

    /**
     * Constructs a deep copy of another time series
     *
     * @param timeSeries another time series
     */
    public TimeSeries(TimeSeries timeSeries) {
        this.name = timeSeries.name;
        this.time2double = new TreeMap<>(timeSeries.time2double);
    }

    /**
     * Insert a time point into the time series
     *
     * @param time  the date of the time point
     * @param value the value of the time point
     */
    public void insert(LocalDate time, double value) {
        time2double.put(time, value);
    }

    /**
     * Gets the value of a time series at a given date
     *
     * @param time the date of the value
     * @return the value of the time series at the given date
     */
    public double getValue(LocalDate time) {
        return time2double.get(time);
    }

    /**
     * Gets the set of all dates contained in the time series
     *
     * @return the set of all dates
     */
    public Set<LocalDate> getTimeSpan() {
        return time2double.keySet();
    }

    /**
     * Gets the name of the time series
     *
     * @return the name of the time series
     */
    public String getName() {
        return name;
    }

    /**
     * Get an iterator over all time points in the time series
     *
     * @return an iterator over all time points in the time series
     */
    @Override
    public Iterator<Map.Entry<LocalDate, Double>> iterator() {
        return time2double.entrySet().iterator();
    }

    /**
     * Gets the name of the time series
     *
     * @return the name of the time series
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Computes the growth rate time series of the given time series
     *
     * @param ts   the time series for which we compute the growth rate
     * @param unit the time unit of the growth rate
     * @return the growth rate time series of the given time series
     */
    public static TimeSeries growthRate(TimeSeries ts, TemporalUnit unit) {
        TimeSeries growthRate = new TimeSeries(ts.getName() + " Growth Rate");
        boolean first = true;
        LocalDate prevTime = ts.time2double.firstKey();
        Double prevValue = ts.time2double.firstEntry().getValue();
        for (Map.Entry<LocalDate, Double> e : ts.time2double.entrySet()) {
            LocalDate time = e.getKey();
            Double value = e.getValue();
            if (first) {
                first = false;
                prevTime = time;
                prevValue = value;
                continue;
            }
            long dur = prevTime.until(time, unit);
            System.out.println(dur);
            double changeRate = (value - prevValue) / dur;
            growthRate.insert(time, changeRate);
            prevTime = time;
            prevValue = value;
        }
        return growthRate;
    }

    /**
     * Computes the moving average time series of the given time series
     *
     * @param ts         the time series for which we compute the moving average
     * @param windowSize the window size of the moving average
     * @return the moving average time series of the given time series
     */
    public static TimeSeries simpleMovingAverage(TimeSeries ts, int windowSize) {
        TimeSeries simpleMovingAverage =
                new TimeSeries(ts.getName() + " Simple Moving Average (window: " + windowSize + ")");
        Queue<Double> windowQueue = new LinkedList<>();
        for (Map.Entry<LocalDate, Double> e : ts.time2double.entrySet()) {
            LocalDate time = e.getKey();
            Double value = e.getValue();
            windowQueue.add(value);
            if (windowQueue.size() < windowSize) {
                continue;
            }
            double sum = 0;
            for (double p : windowQueue) {
                sum += p;
            }
            simpleMovingAverage.insert(time, sum / windowSize);
            windowQueue.remove();
        }
        return simpleMovingAverage;
    }

    /**
     * Get the overlapping dates of all the time series in the list
     *
     * @param timeSeriesList the list of time series
     * @return the set of dates that every time series in the list contains
     */
    public static Set<LocalDate> getOverlapTime(List<TimeSeries> timeSeriesList) {
        if (timeSeriesList.isEmpty()) return new TreeSet<>();
        List<Set<LocalDate>> timeSpanList = new ArrayList<>();
        for (TimeSeries timeSeries : timeSeriesList) {
            timeSpanList.add(timeSeries.getTimeSpan());
        }
        Set<LocalDate> result = timeSpanList.get(0);
        for (Set<LocalDate> timeSpan : timeSpanList) {
            result.retainAll(timeSpan);
        }
        return result;
    }

    //package private for testing
    String getStringRepresentation() {
        return time2double.toString();
    }
}
