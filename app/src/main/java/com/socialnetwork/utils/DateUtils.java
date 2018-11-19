package com.socialnetwork.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    /**
     * Method to return the difference in time
     *
     * @param time Time to get difference from current
     * @return Difference in time
     */
    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    /**
     * Returns an instance of calender
     *
     * @return an instance of calender
     */
    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar.getTime();
    }

    /**
     * @param dateFormat for desired date format
     * @return current date
     */
    public static String getCurrentDate(String dateFormat) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return df.format(c.getTime());
    }

    /**
     * @return current date in dd/MMM/yy format
     */
    public static String getCurrentDate() {
        return getCurrentDate("dd/MM/yyyy");
    }

    public static String getDate(long milliSeconds, String dateFormat, boolean timezone) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds * 1000);

        if (timezone) {
            calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
        }
        return formatter.format(calendar.getTime());
    }


    public static String parseTime(String time) {
        String inputPattern = "hh:mm a";
        String outputPattern = "hh:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


}
