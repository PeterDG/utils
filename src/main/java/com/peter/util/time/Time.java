package com.peter.util.time;

import com.peter.util.exceptions.TechnicalException;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by PEDRO GUTIERREZ on 25/11/2015.
 */
public class Time {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DEFAULT_TIME_ZONE = "GMT-5:00";
    public static final Map<String, String> months;

    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("JANUARY", "01");
        aMap.put("FEBRUARY", "02");
        aMap.put("MARCH", "03");
        aMap.put("APRIL", "04");
        aMap.put("MAY", "05");
        aMap.put("JUNE", "06");
        aMap.put("JULY", "07");
        aMap.put("AUGUST", "08");
        aMap.put("SEPTEMBER", "09");
        aMap.put("OCTOBER", "10");
        aMap.put("NOVEMBER", "11");
        aMap.put("DECEMBER", "12");
        months = Collections.unmodifiableMap(aMap);
    }


    public Time() {
    }

    public static Date string2Date(final String dateStr, String format, String timeZoneID) throws TechnicalException {
        Date date = string2LocalDateTime(dateStr, format).toDate(TimeZone.getTimeZone(timeZoneID));
        return date;
    }

    public static Date string2Date(final String dateStr, String format) throws TechnicalException {
        Date date = string2Date(dateStr, format, DEFAULT_TIME_ZONE);
        return date;
    }

    public static Date string2Date(final String dateStr) throws TechnicalException {
        return string2Date(dateStr, DEFAULT_DATE_FORMAT);
    }

    public static Date string2IsoDate(final String dateStr) throws TechnicalException {
        return string2Date(dateStr, DEFAULT_ISO_DATE_FORMAT);
    }

    public static LocalDateTime string2LocalDateTime(final String dateStr, String format) throws TechnicalException {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(format).withLocale(Locale.US);
            return LocalDateTime.parse(filteredDateStr(dateStr), formatter);
        } catch (Exception pe) {
            throw new TechnicalException("Unable to parse the date: " + dateStr + " as " + format + " " + pe.getMessage());
        }
    }

    public static DateTime string2DateTime(final String dateStr, String format) throws TechnicalException {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
            return DateTime.parse(filteredDateStr(dateStr), formatter);
        } catch (Exception pe) {
            throw new TechnicalException("Unable to parse the date: " + dateStr + " as " + format + " " + pe.getMessage());
        }
    }

    public static LocalDateTime string2LocalDateTime(final String dateStr) throws TechnicalException {
        return string2LocalDateTime(dateStr, DEFAULT_DATE_FORMAT);
    }

    public static LocalDateTime string2LocalDateTimeIsoDate(final String dateStr) throws TechnicalException {
        return string2LocalDateTime(dateStr, DEFAULT_ISO_DATE_FORMAT);
    }

    public static String localDateTime2String(LocalDateTime date, String format) {
        if (null == date)
            return "";
        return DateTimeFormat.forPattern(format).print(date);
    }

    public static String date2String(Date date, String format) {
        if (null == date)
            return "";
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String localDateTime2String(LocalDateTime date) {
        return localDateTime2String(date, DEFAULT_DATE_FORMAT);
    }

    public static String localDateTime2StringIsoDate(LocalDateTime date) {
        return localDateTime2String(date, DEFAULT_ISO_DATE_FORMAT);
    }

    public static LocalDateTime getLastDateOfMonth(final LocalDateTime initial) {
        int lastDay = initial.dayOfMonth().withMaximumValue().getDayOfMonth();
        return new LocalDateTime(initial.getYear(), initial.getMonthOfYear(), lastDay, 23, 59, 59);
    }

    public static LocalDateTime getFirstDateOfTheMonth(final LocalDateTime initial) {
        return new LocalDateTime(initial.getYear(), initial.getMonthOfYear(), 1, 0, 0, 0);
    }

    public static boolean isDate(String dateToValidate, String dateFromat) {
        if (dateToValidate == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
//            System.out.println(date);
        } catch (ParseException e) {
            // e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean isDate(String dateToValidate) {
        return isDate(dateToValidate, DEFAULT_DATE_FORMAT);
    }

    public static boolean isISODate(String dateToValidate) {
        return isDate(dateToValidate, DEFAULT_ISO_DATE_FORMAT);
    }

    public static String filteredDateStr(String dateStr) throws TechnicalException {
        if (!dateStr.contains("-"))
            throw new TechnicalException("Invalid format for date :" + dateStr);
        String[] arrayStr = dateStr.split("-");
        String tmpMonth = arrayStr[1];
        int size = tmpMonth.length();
        if (size == 1 && tmpMonth.matches("\\d+"))
            return arrayStr[0] + "-" + "-0" + tmpMonth + "-" + arrayStr[2];
        else if (size == 2 && tmpMonth.matches("\\d+"))
            return dateStr;
        else if (size > 2 && !tmpMonth.matches("\\d+")) {
            String keyMonth = tmpMonth.toUpperCase();
            if (months.containsKey(keyMonth))
                return arrayStr[0] + "-" + months.get(keyMonth) + "-" + arrayStr[2];
        }
        throw new TechnicalException("Invalid month");
    }

    public static int getWeekOfYear(final String year, final String month, final String day) throws TechnicalException {
        LocalDateTime date = new LocalDateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
//        logger.debug("Date of the week: " + getStringFromDate(date));
        //return date.get(WeekFields.of(Locale.US).weekOfWeekBasedYear());
        return date.getWeekOfWeekyear();
//        logger.debug("Number of week: " + i);
    }

    /**
     * Retrieves the date corresponding to the beginning of the hour ##:00:00
     * E.g.: 09:34:26 will yield to 09:00:00
     *
     * @param initial The date containing the hour to evaluate
     * @return the formatted hour reset to zeros
     */
    public static LocalDateTime getMinimumHourOfTheDay(final LocalDateTime initial) {
        return new LocalDateTime(initial.getYear(), initial.getMonthOfYear(), initial.getDayOfMonth(), initial.getHourOfDay(), 0, 0);
    }

    /**
     * Retrieves the date corresponding to the ending of the hour ##:59:59
     * E.g.: 09:34:26 will yield to 09:59:59
     *
     * @param initial The date containing the hour to evaluate
     * @return the formatted hour set to maximum minutes and seconds
     */
    public static LocalDateTime getMaximumHourOfTheDay(final LocalDateTime initial) {
        return new LocalDateTime(initial.getYear(), initial.getMonthOfYear(), initial.getDayOfMonth(), initial.getHourOfDay(), 59, 59);
    }

    /**
     * Retrieves the date corresponding to the beginning in the morning 00:00:00
     *
     * @param initial The date containing the day to evaluate
     * @return the first date of the initial day
     */
    public static LocalDateTime getFirstDateOfTheDay(final LocalDateTime initial) {
        return new LocalDateTime(initial.getYear(), initial.getMonthOfYear(), initial.getDayOfMonth(), 0, 0, 0);
    }

    /**
     * Retrieves the date corresponding to the ending of the day in the night at 23:59:59
     *
     * @param initial The date containing the day to evaluate
     * @return the last date of the initial day
     */
    public static LocalDateTime getEndingDateOfDay(final LocalDateTime initial) {
        return new LocalDateTime(initial.getYear(), initial.getMonthOfYear(), initial.getDayOfMonth(), 23, 59, 59);
    }

    /**
     * Retrieves all the months contained between the initial and the ending dates
     *
     * @param initial the initial date (Inclusive)
     * @param ending  the ending date (Inclusive)
     * @return the list of month numbers belonging to those dates
     * @throws TechnicalException if ending date is before the initial date
     */
    public static List<LocalDateTime> getDatesListByMonth(final LocalDateTime initial, final LocalDateTime ending) throws TechnicalException {
        if (initial.isAfter(ending))
            throw new TechnicalException("Error: The initial date is after the ending date");
        List<LocalDateTime> months = new ArrayList<>();
        LocalDateTime ini_month = getFirstDateOfTheMonth(initial);
        LocalDateTime end_month = getFirstDateOfTheMonth(ending);
        do {
            months.add(ini_month);
            ini_month = ini_month.plusMonths(1);
        } while (!ini_month.isAfter(end_month));
        return months;
    }

    /**
     * Retrieves all the days contained between the initial and the ending dates
     *
     * @param initial the initial date (Inclusive)
     * @param ending  the ending date (Inclusive)
     * @return the list of month numbers belonging to those dates
     * @throws TechnicalException if ending date is before the initial date
     */
    public static List<LocalDateTime> getDatesListByDay(final LocalDateTime initial, final LocalDateTime ending) throws TechnicalException {
        if (initial.isAfter(ending)) {
            throw new TechnicalException("Error: Initial date after ending date");
        }
        LocalDateTime firstDate = getFirstDateOfTheDay(initial);
        LocalDateTime lastDate = getEndingDateOfDay(ending);
        List<LocalDateTime> datesDayList = new ArrayList<>();
        int days = 0;
        LocalDateTime tmpDate = firstDate.plusDays(days);
        while (tmpDate.isBefore(lastDate)) {
            datesDayList.add(tmpDate);
            days++;
            tmpDate = firstDate.plusDays(days);
        }
        return datesDayList;
    }


    /**
     * This method returns the number of weeks and the dates of weeks
     * from a specific set of dates.
     * Example: If the date parameter is 4 May 2015 12:45, this
     * method will get all the weeks from May 2015 and its respective
     * week numbers of the entire year
     *
     * @param initial start date
     * @param ending  end date
     * @return A list of dates containing the w
     * @throws TechnicalException If current dates do not belong to same year and month
     */
    public static List<LocalDateTime> getDatesListByWeek(final LocalDateTime initial, final LocalDateTime ending) throws TechnicalException {

        if (initial.isAfter(ending)) {
            throw new TechnicalException(String.format("initial date: %s, is after ending date: %s",
                    localDateTime2String(initial), localDateTime2String(ending)));
        }

        List<LocalDateTime> dates = new ArrayList<>();
        LocalDateTime with = getFirstDateOfTheWeek(initial);
        do {
            dates.add(with);
            with = with.plusWeeks(1);
        } while (with.isBefore(ending));
        return dates;
    }


    public static LocalDateTime getFirstDateOfTheWeek(final LocalDateTime initial) {
        LocalDateTime initial1 = initial.withDayOfWeek(DateTimeConstants.MONDAY);
        return getFirstDateOfTheDay(initial1);
    }

    public static LocalDateTime getLastDateOfTheWeek(final LocalDateTime initial) {
        return getEndingDateOfDay(initial.withDayOfWeek(DateTimeConstants.SUNDAY));
    }


    public static boolean fullyCoversTimeFrame(final LocalDateTime tmpDate, final LocalDateTime ending, final int minutes) {
        return !ending.isBefore(tmpDate.plusSeconds(minutes * 60 - 1));
    }

    public static String getCurrentTimeAsString() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MMM-dd HH:mm:ss.ss");
        return formatter.print(LocalDateTime.now());
    }

    public static String getStringMonthFromDate(final LocalDateTime date) {
        return DateTimeFormat.forPattern("MMMM").print(date);
    }

    public static String getStringYearFromDate(final LocalDateTime date) {
        return DateTimeFormat.forPattern("YYYY").print(date);
    }

    //
    public static java.util.Date getDateFromLDT(LocalDateTime localDateTime) {
        return localDateTime.toDate();//TODO remove this method as soon as MongoDD java driver supports LocalDateTime 'Codec'
    }

    public static LocalDateTime getLDTFromDate(java.util.Date date) {
        return new LocalDateTime(date);//TODO remove this method as soon as MongoDD java driver supports LocalDateTime 'Codec'
    }

    public static String getStringYearMonthFromDate(LocalDateTime date) {
        return DateTimeFormat.forPattern("yyyy-MM").print(date);
    }

    public static Date addTimes2Date(Date date, Map<Integer, Integer> calendarTimes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for (Integer time : calendarTimes.keySet())
            calendar.add(time, calendarTimes.get(time));
        return calendar.getTime();
    }

    public static Instant date2Instant(Date date) {
        return new Instant(date.getTime() - 5 * 3600000);
    }

    public static Date getCurrentTime() {
        return new Date();
    }

    public static int getCurrentTime(int calentarTime) {
        java.util.Date date = new Date();
        int month = 0;
        if (calentarTime == Calendar.MONTH)
            month += 1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        month += cal.get(calentarTime);
        return month;
    }

    public static Date setTimes2Date(Date date, Map<Integer, Integer> calendarTimes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for (Integer time : calendarTimes.keySet()) {
            int value = calendarTimes.get(time);
            if (time == Calendar.MONTH)
                value -= 1;
            calendar.set(time, value);
        }
        return calendar.getTime();
    }

    public static java.time.LocalDateTime parseList(CharSequence dateStr, ArrayList<java.time.format.DateTimeFormatter> formats) {
        java.time.LocalDateTime localDateTime = null;
        java.time.format.DateTimeFormatter formatter = formats.get(0);
        try{
            localDateTime=java.time.LocalDateTime.parse(dateStr,formatter);
        } catch(java.time.format.DateTimeParseException e) {
            formats.remove(formatter);
            if (formats.size() > 0) {
                localDateTime=parseList(dateStr, formats);
            } else {
                e.printStackTrace();
            }
        }
        return localDateTime;
    }

}
