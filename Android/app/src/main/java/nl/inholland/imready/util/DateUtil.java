package nl.inholland.imready.util;


import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    public static long getTimeDifferenceDays(Date from, Date to) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(from);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(to);
        long msDiff = toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(msDiff);
    }
}
