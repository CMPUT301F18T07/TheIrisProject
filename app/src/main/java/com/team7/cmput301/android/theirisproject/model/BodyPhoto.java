/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.graphics.Bitmap;

import com.team7.cmput301.android.theirisproject.ImageConverter;

import io.searchbox.annotations.JestId;

/**
 * BodyPhoto is a model to parse bodyphoto type object to from
 * our database
 *
 * @author itstc
 * */
public class BodyPhoto {

    @JestId
    private String _id;
    private Bitmap photo;

    public BodyPhoto(Bitmap image) {
        this.photo = image;
    }

    public String getId() {
        return _id;
    }

    public Bitmap getPhoto() {
        return photo;
    }
}