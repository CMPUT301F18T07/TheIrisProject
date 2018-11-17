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
import java.util.List;

public class RecordTest {

    private String problemID = "mgfdj23";
    private String title = "Title";
    private String desc = "Text";
    private Date date = new Date();
    private GeoLocation geoLocation = null;
    private List<BodyLocation> bodyLocations = null;

    @Test
    public void testRecord(){
        String title = "Title";
        String text = "Text";
        Date date = new Date();
        GeoLocation geoLocation = null;
        List<BodyLocation> bodyLocations = new ArrayList<BodyLocation>();

        Record record = getTestRecord();

        Assert.assertEquals(problemID, record.getProblemId());
        Assert.assertEquals(title, record.getTitle());
        Assert.assertEquals(desc, record.getDesc());
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

        GeoLocation updatedGeoLocation = new GeoLocation(1.0, 1.0);

        Assert.assertEquals(updatedGeoLocation, record.getGeoLocation());
    }

    @Test
    public void testEditBodyLocation() {
        Record record = getTestRecord();

        List<BodyLocation> updatedBodyLocation = new ArrayList<>();

        Assert.assertEquals(updatedBodyLocation, record.getBodyLocations());
    }

    private Record getTestRecord() {
        String title = "Title";
        String text = "Text";
        Date date = new Date();
        GeoLocation geoLocation = null;
        List<BodyLocation> bodyLocations = new ArrayList<>(null);

        Record record = new Record(title, text, date, geoLocation, bodyLocations);
        return new Record(title, desc, date, geoLocation, bodyLocations);
    }

}