/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.BodyLocation;
import com.team7.cmput301.android.theirisproject.model.GeoLocation;
import com.team7.cmput301.android.theirisproject.model.Record;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class RecordTest {

    private String problemID = "mgfdj23";
    private String title = "Title";
    private String text = "Text";
    private Date date = new Date();
    private GeoLocation geoLocation = null;
    private ArrayList<BodyLocation> bodyLocations = null;

    @Test
    public void testRecord(){

        Record record = getTestRecord();

        Assert.assertEquals(problemID, record.getProblemId());
        Assert.assertEquals(title, record.getTitle());
        Assert.assertEquals(text, record.getText());
        Assert.assertEquals(date, record.getDate());
        Assert.assertEquals(geoLocation, record.getGeoLocation());
        Assert.assertEquals(bodyLocations, record.getBodyLocations());

    }

    @Test
    public void testAddPhoto() {
        Record record = getTestRecord();

        record.addPhoto(null);
        Assert.assertEquals(record.getBodyLocations().size(), 1);
    }

    @Test
    public void testDeletePhoto() {
        Record record = getTestRecord();

        record.deletePhoto(null);
        Assert.assertEquals(record.getBodyLocations().size(), 0);
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

        Assert.assertEquals(updatedBodyLocation, record.getBodyLocations());
    }

    private Record getTestRecord() {

        Record record = new Record(title, text, date, geoLocation, bodyLocations);
        return record;

    }

}