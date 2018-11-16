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
 * @author Jmmxp
 */
public class Record {

    @JestId
    private String _id;
    private String problemId;

    private String text;
    private String title;
    private Date date;
    private GeoLocation geoLocation;
    private BodyLocation bodyLocation;
    private ArrayList<RecordPhoto> recordPhotos;

    /* Constructors */

    public Record(String problemId, String title, String text, Date date, GeoLocation geoPt, BodyLocation bodyPt, ArrayList<RecordPhoto> recordPhotos) {
        this.problemId = problemId;
        this.title = title;
        this.text = text;
        this.date = date;
        this.geoLocation = geoPt;
        this.bodyLocation = bodyPt;
        this.recordPhotos = recordPhotos;
    }

    /* Basic getters */

    public String getId() {
        return _id;
    }

    public String getProblemId() {
        return problemId;
    }

    public String getText() {
        return text;
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

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public ArrayList<RecordPhoto> getRecordPhotos() {
        return recordPhotos;
    }

    /* Basic list operations */

    public void addPhoto(RecordPhoto img) {

    }

    public void deletePhoto(RecordPhoto img) {

    }

    /* Advanced setters */

    public void editGeoLocation(int x, int y) {

    }

    public void editBodyLocation(int x, int y) {

    }

}