package com.richardradics.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rcsk on 17/11/2014.
 */
public class DateUtil {

    public static String DATE_FORMAT = "yyyy.MM.dd";

    public static long MILLISECONDS_ONE_WEEK = 86400 * 7 * 1000;
    public static long MILLISECONDS_TWO_WEEK = 86400 * 7 * 1000*2;

    private static SimpleDateFormat defaultSimpleDateFormatter = new SimpleDateFormat(DATE_FORMAT);

    public static final String DF_LONG = "yyyy.MM.dd HH:mm:ss";
    public static final String DF_TIME = "HH:mm";
    public static final String DF_DEF = "yyyy.MM.dd.";

    /**
     * Return a SimpleDateFormat with the specified pattern
     * @param pattern
     * @return
     */
    public static DateFormat getSimplDateFormat(String pattern) throws IllegalArgumentException {
        if(pattern == null){
            return null;
        }
        return new SimpleDateFormat(pattern);
    }

    /**
     * Return a SimpleDateFormat with pattern: yyyy.MM.dd HH:mm:ss
     * @return
     */
    public static DateFormat getSimplDateFormat() {
        return new SimpleDateFormat(DF_LONG);
    }

    /**
     * Return a SimpleDateFormat with pattern: yyyy.MM.dd.
     * @return
     */
    public static DateFormat getSimplDateFormatDay() {
        return new SimpleDateFormat(DF_DEF);
    }

    /**
     * Return a SimpleDateFormat with pattern: HH:mm
     * @return
     */
    public static DateFormat getSimplDateFormatTime() {
        return new SimpleDateFormat(DF_TIME);
    }

    /**
     * Returns the date as string in the format: yyyy.MM.dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        return getSimplDateFormat(DF_LONG).format(date);
    }

    /**
     * Returns the date as string in the format: yyyy.MM.dd.
     * @param date
     * @return
     */
    public static String formatDateDay(Date date){
        return getSimplDateFormat(DF_DEF).format(date);
    }

    /**
     * Returns the date as string in the format: HH:mm
     * @param date
     * @return
     */
    public static String formatDateTime(Date date){
        return getSimplDateFormat(DF_TIME).format(date);
    }

    /**
     * Returns date in the specified pattern
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern){
        return getSimplDateFormat(pattern).format(date);
    }

    /**
     * Returns date parsed from string in format: yyyy.MM.dd HH:mm:ss
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateString) throws ParseException {
        return getSimplDateFormat(DF_LONG).parse(dateString);
    }

    /**
     * Returns date parsed from string in format: yyyy.MM.dd.
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parseDateDay(String dateString) throws ParseException{
        return getSimplDateFormat(DF_DEF).parse(dateString);
    }

    /**
     * Returns date parsed from string by given pattern
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parseDateLong(String dateString, String pattern) throws ParseException{
        return getSimplDateFormat(pattern).parse(dateString);
    }


}