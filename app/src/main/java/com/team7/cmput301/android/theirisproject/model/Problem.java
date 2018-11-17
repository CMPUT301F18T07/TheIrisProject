/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import com.google.gson.JsonArray;
import com.team7.cmput301.android.theirisproject.ImageConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.searchbox.annotations.JestId;

/**
 * Represents a particular issue that a Patient has.
 *
 * @see ProblemList
 * @see Patient
 * @author itstc
 * @author jtfwong
 */
public class Problem {

    @JestId
    private String _id;

    private String title;
    private String user;
    private Date date;
    private String description;
    transient private RecordList records;
    transient private List<Comment> comments = new ArrayList<>();
    transient private List<BodyPhoto> bodyPhotos;

    private List<String> recordsId;
    private List<String> bodyPhotoBlobs;
    private List<String> commentIds;

    public Problem(String title, String description, String user, List<BodyPhoto> bodyPhotos) {
        this(title, description, user);
        this.bodyPhotos = bodyPhotos;
        this.bodyPhotoBlobs = new ArrayList<>();
        for (BodyPhoto bp: bodyPhotos) {
            bodyPhotoBlobs.add(ImageConverter.base64EncodeBitmap(bp.getPhoto()));
        }
        this.date = new Date();
    }

    public Problem(String title, String description, String user) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.date = new Date();
    }

    public Problem() {

    }

    /* Basic setters */

    public void setRecords(RecordList records){
        this.records = records;
    }

    /* Basic getters */

    public RecordList getRecords() {
        return records;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getId() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUser() {return user;}

    public List<String> getRecordsId() {
        return recordsId;
    }

    public List<String> bodyPhotoBlobs() {
        return bodyPhotoBlobs;
    }

    public List<String> getCommentIds() {
        return commentIds;
    }

    public List<BodyPhoto> getBodyPhotos() {
        return bodyPhotos;
    }

    /* Advanced getters */

    public List<RecordPhoto> getSlideShowInfo() {
        return null;
    }

    /* ElasticSearch handlers */

    public void convertBlobsToBitmaps() {
        ArrayList<BodyPhoto> newPhotos = new ArrayList<>();
        for (String blob: bodyPhotoBlobs) {
            newPhotos.add(new BodyPhoto(ImageConverter.base64DecodeBitmap(blob)));
        }
        bodyPhotos = newPhotos;
    }

}