/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.helper;

import java.util.List;

/**
 * Helper class that has helper methods pertaining to Strings and formatting Strings
 *
 * @author Jmmxp
 */
public class StringHelper {

    /**
     * Given a List of Strings and a separator delimiter, formats the List of Strings into one String
     * @param strings The Strings to separate
     * @param separator The separator to put between each strings
     * @return Strings conacatenated with one another with separator in between each
     */
    public static String join(List<String> strings, String separator) {
        if (strings == null || strings.size() == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            String id = strings.get(i);

            if (i != strings.size() - 1) {
                builder.append(id + separator);
            } else {
                builder.append(id);
            }
        }

        return builder.toString();
    }

    public static void addQuotations(List<String> strings) {
        if (strings == null || strings.size() == 0) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);
            builder.append("\"" + string + "\"");

            strings.set(i, builder.toString());
            builder.setLength(0);
        }
    }

    /**
     * Given a list of strings, return all of them concatenated in the form of a JSON array e.g. ["str1", "str2", "str3"]
     * @param strings
     * @return the JSON Array String
     */
    public static String generateJSONArrayString(List<String> strings) {
        if (strings == null || strings.size() == 0) {
            // Return empty list
            return "[]";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        int size = strings.size();
        for (int i = 0; i < size; i++) {
            String string = strings.get(i);
            if (i == size - 1) {
                builder.append("\"" + string + "\"");
            } else {
                builder.append("\"" + string + "\", ");
            }
        }

        builder.append("]");
        return builder.toString();
    }

    /**
     * Given a List of Strings, determine if it has an empty or null string
     * @param strings
     */
    public static boolean hasEmptyString(List<String> strings) {
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
                return true;
            }
        }

        return false;
    }

}
