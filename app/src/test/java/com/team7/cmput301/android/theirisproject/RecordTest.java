/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.GeoLocation;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordTest {

    private String problemID = "mgfdj23";
    private String title = "Title";
    private String desc = "Text";
    private Date date = new Date();
    private GeoLocation geoLocation = null;
    private List<RecordPhoto> recordPhotos = null;

    @Test
    public void testRecord(){
        String title = "Title";
        String text = "Text";
        Date date = new Date();
        GeoLocation geoLocation = null;
        List<RecordPhoto> recordPhotos = new ArrayList<>();

        Record record = getTestRecord();

        Assert.assertEquals(problemID, record.getProblemId());
        Assert.assertEquals(title, record.getTitle());
        Assert.assertEquals(desc, record.getDesc());
        Assert.assertEquals(date, record.getDate());
        Assert.assertEquals(geoLocation, record.getGeoLocation());
        Assert.assertEquals(recordPhotos, record.getRecordPhotos());

    }

    @Test
    public void testAddPhoto() {
        Record record = getTestRecord();

        record.addPhoto(null);
        Assert.assertEquals(record.getRecordPhotos().size(), 1);
    }

    @Test
    public void testDeletePhoto() {
        Record record = getTestRecord();

        record.deletePhoto(null);
        Assert.assertEquals(record.getRecordPhotos().size(), 0);
    }

    @Test
    public void testEditGeoLocation() {
        Record record = getTestRecord();

        GeoLocation updatedGeoLocation = new GeoLocation(1.0, 1.0);

        Assert.assertEquals(updatedGeoLocation, record.getGeoLocation());
    }

    @Test
    public void testEditRecordPhotos() {
        Record record = getTestRecord();

        List<RecordPhoto> recordPhotos = new ArrayList<>();

        Assert.assertEquals(recordPhotos, record.getRecordPhotos());
    }

    private Record getTestRecord() {

        String problemId = "mwmwmw";
        String title = "Title";
        String text = "Text";
        Date date = new Date();
        GeoLocation geoLocation = new GeoLocation(1.0, 1.0);
        List<RecordPhoto> recordPhotos = new ArrayList<>();

        return new Record(problemId, title, text, date, geoLocation, recordPhotos);

    }

}