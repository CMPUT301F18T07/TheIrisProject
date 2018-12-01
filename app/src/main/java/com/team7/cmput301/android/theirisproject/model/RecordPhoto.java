/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.graphics.Bitmap;

import com.team7.cmput301.android.theirisproject.ImageConverter;

import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * A photo associated with a Record.
 *
 * @see Record
 * @author jtfwong
 * @author anticobalt
 */
public class RecordPhoto extends Photo {

    @JestId
    private String _id;
    private String recordId;
    private String blob;
    private Date date;
    transient private Bitmap photo;

    /* Constructors */

    public RecordPhoto(Bitmap photo) {
        this.photo = photo;
        this.blob = ImageConverter.base64EncodeBitmap(photo);
        this.date = new Date();
    }

    public RecordPhoto(String recordId, String blob) {
        this.recordId = recordId;
        this.photo = ImageConverter.base64DecodeBitmap(blob);
        this.blob = blob;
    }

    /* Basic getters */
    public String getId() { return _id; }
    public Bitmap getPhoto() {
        return photo;
    }
    public Date getDate() { return date; }

    /* Basic setters */
    public void setRecordId(String id) { this.recordId = id; }
    public void setDate(Date date) { this.date = date; }

    /* Advanced setters */
    public void convertBlobToBitmap() {
        photo = ImageConverter.base64DecodeBitmap(blob);
    }


}