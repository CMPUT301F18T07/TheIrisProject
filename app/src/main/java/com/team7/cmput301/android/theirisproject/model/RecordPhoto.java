/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.graphics.Bitmap;

/**
 * A photo associated with a Record.
 *
 * @see Record
 * @author Jmmxp
 */
public class RecordPhoto {

    private Bitmap img;

    /* Constructors */

    public RecordPhoto(Bitmap photo) {

    }

    /* Basic getters */

    public Bitmap getImg() {
        return img;
    }


}