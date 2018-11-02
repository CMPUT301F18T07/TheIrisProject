/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.BodyLocation;
import com.team7.cmput301.android.theirisproject.model.GeoLocation;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class RecordTest {
    @Test
    public void testRecord(){
        String title = "Title";
        String text = "Text";
        Date date = new Date();
        GeoLocation geoLocation = null;
        BodyLocation bodyLocation = null;
        ArrayList<RecordPhoto> recordPhoto = new ArrayList<>(null);

        Record record = new Record(title, text, date, geoLocation, bodyLocation, recordPhoto);

        Assert.assertEquals(title, record.getTitle());
        Assert.assertEquals(text, record.getText());
        Assert.assertEquals(date, record.getDate());
        Assert.assertEquals(geoLocation, record.getGeoLocation());
        Assert.assertEquals(bodyLocation, record.getBodyLocation());
        Assert.assertEquals(recordPhoto, record.getRecordPhoto());
    }

    @Test
    public void testAddPhoto() {
        Record record = getTestRecord();

        record.addPhoto(null);
        Assert.assertEquals(record.getRecordPhoto().size(), 1);
    }

    @Test
    public void testDeletePhoto() {
        Record record = getTestRecord();

        record.deletePhoto(null);
        Assert.assertEquals(record.getRecordPhoto().size(), 0);
    }

    @Test
    public void testEditGeoLocation() {
        Record record = getTestRecord();

        GeoLocation updatedGeoLocation = null;

        Assert.assertEquals(updatedGeoLocation, record.getGeoLocation());
    }

    @Test
    public void testEditBodyLocation() {
        Record record = getTestRecord();

        BodyLocation updatedBodyLocation = null;

        Assert.assertEquals(updatedBodyLocation, record.getBodyLocation());
    }

    private Record getTestRecord() {
        String title = "Title";
        String text = "Text";
        Date date = new Date();
        GeoLocation geoLocation = null;
        BodyLocation bodyLocation = null;
        ArrayList<RecordPhoto> recordPhotos = new ArrayList<>(null);

        Record record = new Record(title, text, date, geoLocation, bodyLocation, recordPhotos);
        return record;
    }

}