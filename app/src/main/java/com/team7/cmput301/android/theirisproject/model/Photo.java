package com.team7.cmput301.android.theirisproject.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public abstract class Photo {

    public String getMetaData() {
        return "";
    }
    public abstract Bitmap getPhoto();
    public abstract Date getDate();
}
