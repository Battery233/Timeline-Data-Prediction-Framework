package edu.cmu.cs.cs214.hw5.core.datastructures;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * The immutable time point class that represents a time point
 * Note that time is represented as LocalDate (YYYY-MM-DD)
 * and the value of the time point is represented as double
 */
public class TimePoint {
    private final LocalDate time;
    private final double value;
    private final String name;
    private static final Comparator<TimePoint> COMPARE_BY_VALUE = Comparator.comparingDouble(TimePoint::getValue);

    /**
     * Constructs a time point with the given time, value, and name
     *
     * @param time  the time of the time point
     * @param value the value of the time point
     * @param name  the name of the time point
     */
    public TimePoint(LocalDate time, double value, String name) {
        this.time = time;
        this.value = value;
        this.name = name;
    }

    /**
     * Gets the value of the time point
     *
     * @return the value of the time point
     */
    public double getValue() {
        return value;
    }

    /**
     * Gets the time of the time point
     *
     * @return the time of the time point
     */
    public LocalDate getTime() {
        return time;
    }

    /**
     * Gets the name of the time point
     *
     * @return the name of the time point
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name of the time point
     *
     * @return the name of the time point
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Sorts the time point list (in-place) according to each time point's value
     *
     * @param timePointList the time point list to be sorted
     */
    public static void sortByValue(List<TimePoint> timePointList) {
        timePointList.sort(COMPARE_BY_VALUE);
    }
}
