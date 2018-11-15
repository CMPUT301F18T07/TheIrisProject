/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.helper;

import java.util.List;

/**
 * Helper class that has helper methods pertaining to Strings and formatting Strings
 */
public class StringHelper {

    public static String join(List<String> strings, String separator) {
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

}
