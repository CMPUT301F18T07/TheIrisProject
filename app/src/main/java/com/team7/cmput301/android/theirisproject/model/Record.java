/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

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

    private String desc;
    private String title;
    private Date date;
    private GeoLocation geoLocation;
    private BodyLocation bodyLocation;
    transient private List<RecordPhoto> recordPhotos;

    /* Constructors */

    public Record(String problemId, String title, String desc, Date date, GeoLocation geoPt, List<RecordPhoto> recordPhotos) {
        this.problemId = problemId;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.geoLocation = geoPt;
        this.recordPhotos = recordPhotos;
    }

    public Record(String problemId, String title, String desc, List<RecordPhoto> recordPhotos) {
        this(problemId, title, desc);
        this.recordPhotos = recordPhotos;
    }

    public Record(String problemId, String title, String desc) {
        this();
        this.problemId = problemId;
        this.title = title;
        this.desc = desc;
        this.date = new Date();
    }

    public Record() {
        this.recordPhotos = new ArrayList<>();
    }

    /* Basic setter */

    public void setTitle(String title){
        this.title = title;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setRecordPhotos(List<RecordPhoto> recordPhotos) {
        this.recordPhotos = recordPhotos;
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

    public List<RecordPhoto> getRecordPhotos() {
        return recordPhotos;
    }

    /* Basic list operations */

    public void addPhoto(RecordPhoto img) {
        recordPhotos.add(img);
    }

    public void deletePhoto(RecordPhoto img) {
        recordPhotos.remove(img);
    }

    /* Advanced setters */

    public void editGeoLocation(int x, int y) {

    }

    public synchronized void asyncSetFields(Record record) {
        this._id = record._id;
        this.problemId = record.problemId;
        this.title = record.title;
        this.desc = record.desc;
        this.date = record.date;
    }

    public synchronized void asyncSetRecordPhotos(List<RecordPhoto> recordPhotos) {
        this.recordPhotos = recordPhotos;
    }

}