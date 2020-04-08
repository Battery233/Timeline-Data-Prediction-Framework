package edu.cmu.cs.cs214.hw5.plugin;

import java.util.Calendar;
import java.util.Date;

/**
 * Util class for date processing.
 */
public class DateUtil {

    private DateUtil() {
    }

    /**
     * calculate the interval between two days (both ends included).
     *
     * @param start the start date
     * @param end   the end date
     * @return the interval days
     */
    public static int dateInterval(Date start, Date end) {
        return (int) ((Math.round((double) (end.getTime() - start.getTime()) / 86400000)) + 1);
    }

    /**
     * tool to get a consecutive date array.
     *
     * @param start the start date
     * @param end   the end date
     * @return an date array
     */
    public static Date[] getDateArray(Date start, Date end) {
        int days = dateInterval(start, end);
        Date[] dates = new Date[days];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        for (int i = 0; i < days; i++) {
            dates[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
}
