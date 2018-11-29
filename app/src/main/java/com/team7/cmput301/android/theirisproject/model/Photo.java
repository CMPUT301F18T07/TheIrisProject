/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */


package com.team7.cmput301.android.theirisproject.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Photo is a template class for any Image Models.
 * Used primarily for ImageListAdapter
 *
 * @see com.team7.cmput301.android.theirisproject.ImageListAdapter
 * @author itstc
 * */
public abstract class Photo {

    public String getMetaData() {
        return "";
    }
    public abstract Bitmap getPhoto();
    public abstract Date getDate();
}
