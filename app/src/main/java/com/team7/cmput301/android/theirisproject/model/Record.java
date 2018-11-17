/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * A single record of a particular Problem
 *
 * @see RecordList
 * @see Problem
 * @author jtfwong
 */
public class Record {

    @JestId
    private String _id;
    private String problemId;

    private String desc;
    private String title;
    private Date date;
    private GeoLocation geoLocation;
    transient private ArrayList<BodyLocation> bodyLocations = new ArrayList<BodyLocation>();

    /* Constructors */

    public Record(String title, String desc, Date date, GeoLocation geoPt, ArrayList<BodyLocation> bodyLocations) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.geoLocation = geoPt;
        this.bodyLocations = bodyLocations;
    }

    public Record(String problemId, String title, String desc) {
        this.problemId = problemId;
        this.title = title;
        this.desc = desc;
        this.date = new Date();
    }

    public Record() {

    }

    /* Basic getters */

    public String getId() {
        return _id;
    }

    public String getProblemId() {
        return problemId;
    }

    public String getDesc() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public ArrayList<BodyLocation> getBodyLocations() {
        return bodyLocations;
    }

    /* Basic list operations */

    public void addPhoto(BodyLocation img) {
        bodyLocations.add(img);
    }

    public void deletePhoto(BodyLocation img) {
        bodyLocations.remove(img);
    }

    /* Advanced setters */

    public void editGeoLocation(int x, int y) {

    }

    public void editBodyLocation(int x, int y) {

    }

}