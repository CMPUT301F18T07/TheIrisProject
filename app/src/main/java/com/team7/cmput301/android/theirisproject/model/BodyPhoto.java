/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.graphics.Bitmap;

import io.searchbox.annotations.JestId;

public class BodyPhoto {

    @JestId
    private String _id;

    private String problemID;
    private Bitmap image;

    public void BodyPhoto(String problemID, Bitmap image) {
        this.problemID = problemID;
        this.image = image;
    }

    public String getProblemId() {
        return problemID;
    }

    public String getId() {
        return _id;
    }

    public Bitmap getImage() {
        return image;
    }
}