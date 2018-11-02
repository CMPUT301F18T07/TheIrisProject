/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

public class RecordUnitTest {
    @Test
    public void testCreateRecord() {
        Date date = new Date();
        RecordPhoto photo = new RecordPhoto(null);
        Record rec = new Record("Hello", date, null, null, new ArrayList<RecordPhoto>());

        assertEquals(rec.getTitle(), "Hello");
        assertEquals(rec.getDate(), date);
    }

    public void testAddPhoto() {
        rec.addPhoto(photo);
        assertEquals(rec.getPhotos().size(), 1);
    }

    public void testDeletePhoto() {
        rec.deletePhoto(photo);
        assertEquals(rec.getPhotos().size(), 0);
    }
}