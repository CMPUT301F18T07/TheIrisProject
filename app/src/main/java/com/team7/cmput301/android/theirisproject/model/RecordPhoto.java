/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.graphics.Bitmap;

import com.team7.cmput301.android.theirisproject.ImageConverter;

import io.searchbox.annotations.JestId;

/**
 * A photo associated with a Record.
 *
 * @see Record
 * @author jtfwong
 * @author anticobalt
 */
public class RecordPhoto {

    @JestId
    private String _id;
    private String recordId;

    private int x;
    private int y;
    transient private Bitmap img;
    private String blob;

    /* Constructors */

    public RecordPhoto(String recordId, Bitmap img, int x, int y) {
        this.recordId = recordId;
        this.img = img;
        this.x = x;
        this.y = y;
        this.blob = ImageConverter.base64EncodeBitmap(img);
    }

    public RecordPhoto(String recordId, String blob, int x, int y) {
        this.recordId = recordId;
        this.img = ImageConverter.base64DecodeBitmap(blob);
        this.x = x;
        this.y = y;
        this.blob = blob;
    }

    /* Basic getters */

    public Bitmap getImg() {
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /* Advanced setters */

    public void update(int x, int y) {

    }


}