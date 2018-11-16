/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

/**
 * A specific location on Earth, belonging to a Record.
 *
 * @see Record
 * @author Jmmxp
 */
public class GeoLocation {

    private Double latitude;
    private Double longitude;

    /* Constructors */

    public GeoLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /* Advanced setters */

    public void update(double lat, double lng) {

    }

    /* Misc */

    public double[] asDouble() {
        return new double[0];
    }

}