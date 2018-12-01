/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import io.searchbox.annotations.JestId;

import com.team7.cmput301.android.theirisproject.ImageConverter;
import com.team7.cmput301.android.theirisproject.helper.DateHelper;

import java.text.ParseException;
import java.util.Date;


/**
 * BodyPhoto is a model to parse bodyphoto type object to from
 * our database
 *
 * @see Problem
 * @author itstc
 * */
public class BodyPhoto extends Photo implements Parcelable {

    public static final String FILE_FORMAT = "bp_%s_%s";

    @JestId
    private String _id;
    private String user;
    private String blob;
    private String label;
    private Date date = new Date();
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

    public String getBlob() { return blob; }

    public Date getDate() { return date; }

    @Override
    public String getMetaData() {
        return label;
    }

    public void convertBlobToPhoto() {
        photo = ImageConverter.base64DecodeBitmap(blob);
    }


    /* Parceable methods for BodyPhoto */

    public BodyPhoto(Parcel in) {
        String[] data = new String[4];
        data = in.createStringArray();
        this.user = data[0];
        this.blob = data[1];
        convertBlobToPhoto();
        this.label = data[2];
        try {
            this.date = DateHelper.parse(data[3]);
        } catch (ParseException e) {
            e.printStackTrace();
            this.date = new Date();
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{user, blob, label, DateHelper.format(date)});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public BodyPhoto createFromParcel(Parcel in) {
            return new BodyPhoto(in);
        }

        public BodyPhoto[] newArray(int size) {
            return new BodyPhoto[size];
        }
    };
}