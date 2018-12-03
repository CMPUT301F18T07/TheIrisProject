/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Helper class that will help format dates
 */
public class DateHelper {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);

    /**
     * Formats a date object to a string in the specified date format
     * @param date
     * @return String the date argument as a string in the specified date format
     */
    public static String format(Date date) {
        return dateFormat.format(date);

    }

    /**
     *
     * @param string
     * @return Date date object represented by the string
     * @throws ParseException Throws following exception if string
     * is not correctly formatted to the specified format
     */
    public static Date parse(String string) throws ParseException {
        return dateFormat.parse(string);
    }
}
