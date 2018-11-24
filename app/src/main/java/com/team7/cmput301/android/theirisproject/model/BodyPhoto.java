/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.graphics.Bitmap;
import io.searchbox.annotations.JestId;

import com.team7.cmput301.android.theirisproject.ImageConverter;

import java.util.Date;


/**
 * BodyPhoto is a model to parse bodyphoto type object to from
 * our database
 *
 * @see Problem
 * @author itstc
 * */
public class BodyPhoto implements Photo {

    @JestId
    private String _id;
    private String user;
    private String blob;
    private String label;
    private Date date;
    transient private Bitmap photo;

    /* Constructors */

    public BodyPhoto(String user, Bitmap image, String label) {
        this(user, image);
        this.label = label;
    }

    public BodyPhoto(String user, Bitmap image) {
        this.user = user;
        this.photo = image;
        this.blob = ImageConverter.base64EncodeBitmap(image);
    }

    public BodyPhoto(String user, String blob) {
        this.user = user;
        this.photo = ImageConverter.base64DecodeBitmap(blob);
        this.blob = blob;
    }

    public BodyPhoto(Bitmap image) {
        this.photo = image;
        this.blob = ImageConverter.base64EncodeBitmap(image);
    }

    public void setUser(String id) {
        user = id;
    }

    /* Basic getters */

    public String getId() {
        return _id;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public Date getDate() { return new Date(); }

    public void convertBlobToPhoto() {
        photo = ImageConverter.base64DecodeBitmap(blob);
    }
}