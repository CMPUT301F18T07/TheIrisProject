/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.graphics.Bitmap;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String user;
    private String desc;
    private String title;
    private Date date = new Date();
    private GeoLocation geoLocation;
    private double[] location = new double[2];

    private BodyLocation bodyLocation;
    transient private List<RecordPhoto> recordPhotos = new ArrayList<>();

    public void setLocation(double[] location) {
        this.location = location;
    }

    /* Constructors */

    public Record(String user, String problemId, String title, String desc, Date date, GeoLocation geoPt, List<RecordPhoto> recordPhotos) {
        this(user, problemId, title, desc, geoPt, recordPhotos);
        this.date = date;
    }

    public Record(String user, String problemId, String title, String desc, List<RecordPhoto> recordPhotos) {
        this(user, problemId, title, desc);
        this.recordPhotos = recordPhotos;
    }


    public Record(String user, String problemId, String title, String desc, BodyLocation bodyLocation, List<RecordPhoto> recordPhotos) {
        this(user, problemId, title, desc);
        this.bodyLocation = bodyLocation;
        this.recordPhotos = recordPhotos;
    }
  
    public Record(String user, String problemId, String title, String desc, GeoLocation geoPt, BodyLocation bodyLocation, List<RecordPhoto> recordPhotos) {
        this(user, problemId, title, desc, bodyLocation, recordPhotos);
        this.geoLocation = geoPt;
    }

    public Record(String user, String problemId, String title, String desc) {
        this.user = user;
        this.problemId = problemId;
        this.title = title;
        this.desc = desc;
        this.date = new Date();
    }

    public Record(String user, String problemId, String title, String desc, GeoLocation geoPt, List<RecordPhoto> recordPhotos) {
        this(user, problemId, title, desc);
        this.geoLocation = geoPt;
        this.recordPhotos = recordPhotos;
    }

    public Record() {
    }

    /* Basic setter */

    public void setId(String id) { this._id = id; }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setRecordPhotos(List<RecordPhoto> recordPhotos) {
        this.recordPhotos = recordPhotos;
    }

    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    /* Basic getters */

    public String getUser() { return user; }

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

    public List<RecordPhoto> getRecordPhotos() {
        return recordPhotos;
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    /* Basic list operations */

    public void addRecordPhoto(RecordPhoto img) {
        recordPhotos.add(img);
    }

    public void addRecordPhotos(List<RecordPhoto> photos) {
        recordPhotos.addAll(photos);
    }

    public void deleteRecordPhoto(RecordPhoto img) {
        recordPhotos.remove(img);
    }

    /* Advanced getters */

    public Bitmap getBodyPhotoBitmap() {

        if (bodyLocation == null) {
            return null;
        }
        else {
            BodyPhoto b = IrisProjectApplication.getBodyPhotoById(bodyLocation.getBodyPhotoId());
            if (b != null) {
                return b.getPhoto();
            } else {
                return null;
            }
        }
    }

    /* Advanced setters */

    public void editGeoLocation(double x, double y) {
        geoLocation.setPosition(x, y);
    }

    public synchronized void asyncSetFields(Record record) {
        this._id = record._id;
        this.problemId = record.problemId;
        this.title = record.title;
        this.desc = record.desc;
        this.date = record.date;
        this.geoLocation = record.getGeoLocation();
    }

    public synchronized void asyncSetRecordPhotos(List<RecordPhoto> recordPhotos) {
        this.recordPhotos = recordPhotos;
    }

}