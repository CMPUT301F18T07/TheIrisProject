/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.io.Serializable;

/**
 * A specific location on Earth, belonging to a Record.
 *
 * @see Record
 * @author jtfwong
 */
public class GeoLocation implements Serializable {

    private Double lat;
    private Double lon;

    /* Constructors */

    public GeoLocation(Double latitude, Double longitude) {
        this.lat = latitude;
        this.lon = longitude;
    }

    /* Setters */

    public void setPosition(double lat, double lng) {
        this.lat = lat;
        lon = lng;
    }

    /* Misc */

    public double[] asDouble() {
        double res[] = {lat, lon};
        return res;
    }
  
}