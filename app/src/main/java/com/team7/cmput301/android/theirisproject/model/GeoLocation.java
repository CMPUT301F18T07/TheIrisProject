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
public class GeoLocation implements Serializable{

    private Double latitude;
    private Double longitude;

    /* Constructors */

    public GeoLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /* Setters */

    public void setPosition(double lat, double lng) {
        latitude = lat;
        longitude = lng;
    }

    /* Misc */

    public double[] asDouble() {
        double res[] = {latitude, longitude};
        return res;
    }
  
}