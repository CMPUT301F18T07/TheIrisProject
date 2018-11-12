/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.BodyLocation;
import com.team7.cmput301.android.theirisproject.model.GeoLocation;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;


public class RecordListTest {

    @Test
    public void testAdd() {
        RecordList recordList = new RecordList();
        Record record = getTestRecord();

        Assert.assertEquals(recordList.getRecords().size(), 0);

        recordList.add(record);

        Assert.assertEquals(recordList.getRecords().size(), 1);
    }

    @Test
    public void testContains() {
        RecordList recordList = new RecordList();
        Record record = getTestRecord();

        recordList.add(record);

        Assert.assertEquals(recordList.contains(record), true);
    }

    @Test
    public void testRemove() {
        RecordList recordList = new RecordList();
        Record record = getTestRecord();

        Assert.assertEquals(recordList.getRecords().size(), 1);

        recordList.remove(record);

        Assert.assertEquals(recordList.getRecords().size(), 0);
    }

    @Test
    public void testLength() {
        RecordList recordList = new RecordList();
        Record record = getTestRecord();

        recordList.add(record);

        Assert.assertEquals(recordList.length(), 1);
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