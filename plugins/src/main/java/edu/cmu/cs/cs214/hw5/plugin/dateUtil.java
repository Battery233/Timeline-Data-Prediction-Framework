package edu.cmu.cs.cs214.hw5.plugin;

import java.util.Calendar;
import java.util.Date;

public class dateUtil {

    public static int dateInterval(Date start, Date end) {
        double SECONDS_PER_DAY = 86400000;
        return (int) ((Math.round((double) (end.getTime() - start.getTime()) / SECONDS_PER_DAY)) + 1);
    }

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
