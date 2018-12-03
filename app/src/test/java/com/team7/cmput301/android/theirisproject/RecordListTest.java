/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.GeoLocation;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RecordListTest {

    @Test
    public void testAdd() {
        // Test adding a record to record list

        RecordList recordList = new RecordList();
        Record record = getTestRecord();

        Assert.assertEquals(recordList.asList().size(), 0);
        recordList.add(record);
        Assert.assertEquals(recordList.asList().size(), 1);
        Assert.assertEquals(recordList.asList().get(0), record);
    }

    @Test
    public void testContains() {
        // Test to see if record list contains record

        RecordList recordList = new RecordList();
        Record record = getTestRecord();

        recordList.add(record);

        Assert.assertEquals(recordList.contains(record), true);
        Assert.assertEquals(recordList.asList().get(0), record);
    }

    @Test
    public void testRemove() {
        // Test to delete record from record list

        RecordList recordList = new RecordList();
        Record record = getTestRecord();

        recordList.add(record);
        Assert.assertEquals(recordList.asList().size(), 1);
        Assert.assertEquals(recordList.asList().get(0), record);
        recordList.remove(record);
        Assert.assertEquals(recordList.asList().size(), 0);
    }

    @Test
    public void testLength() {
        // Test to return record list length

        RecordList recordList = new RecordList();
        Record record = getTestRecord();

        recordList.add(record);

        Assert.assertEquals(recordList.length(), 1);
    }

    private Record getTestRecord() {

        String user = "care provider";
        String problemID = "mk543";
        String title = "Title";
        String text = "Text";
        Date date = new Date();
        GeoLocation geoLocation = new GeoLocation(1.0, 1.0);
        List<RecordPhoto> recordPhotos = new ArrayList<>();

        return new Record(user, problemID, title, text, date, geoLocation, recordPhotos);

    }

}