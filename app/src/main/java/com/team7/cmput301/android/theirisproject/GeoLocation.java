/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

public class GeoLocation implements Location {
    @Override
    public double[] asDouble() {
        return new double[2];
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    public double getLatitude() {
        return getX();
    }

    public double getLongitude() {
        return getY();
    }
}
