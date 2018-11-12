/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;
import java.util.Date;

public class Record {
    private String text;
    private String title;
    private Date date;
    private GeoLocation geoLocation;
    private BodyLocation bodyLocation;
    private ArrayList<RecordPhoto> recordPhotos = new ArrayList<RecordPhoto>();

    public Record(String title, String text, Date date, GeoLocation geoPt, BodyLocation bodyPt, ArrayList<RecordPhoto> recordPhotos) {
    }

    public Record() {

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

    public void addPhoto(RecordPhoto img) {

    }

    public void deletePhoto(RecordPhoto img) {

    }

    public void editGeoLocation(int x, int y) {

    }

    public void editBodyLocation(int x, int y) {

    }
}
