package com.team7.cmput301.android.theirisproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.searchbox.annotations.JestId;

/**
 * BodyLocation is a model to store the information of
 * a record's location on a bodyphoto.
 *
 * @see Record
 * @see BodyPhoto
 *
 * @author itstc
 * */
public class BodyLocation {

    private String bodyPhotoId;
    private float x;
    private float y;

    public BodyLocation(String bodyPhotoId, float x, float y) {
        this.bodyPhotoId = bodyPhotoId;
        this.x = x;
        this.y = y;
    }

    public BodyLocation(String bodyPhotoId) {
        this.bodyPhotoId = bodyPhotoId;
    }

    public float[] getLocation() {
        return new float[]{x,y};
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public String getBodyPhotoId() {
        return bodyPhotoId;
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
