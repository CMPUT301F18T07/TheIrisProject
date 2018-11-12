/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.annotations.JestId;

public class Problem {
    @JestId
    private String _id;

    private String title;
    private String desc;
    private String user;
    private RecordList records;
    private List<Comment> comments = new ArrayList<>();
    private List<BodyPhoto> bodyPhotos;

    public Problem(String title, String description, String user, RecordList records, List<BodyPhoto> bodyPhotos) {
        this.title = title;
        this.desc = description;
        this.user = user;
        this.records = records;
        this.bodyPhotos = bodyPhotos;
    }

    public Problem(String title, String description, String user) {
        this.title = title;
        this.desc = description;
        this.user = user;
    }
    public Problem() {

    }

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

    public String getDescription() {
        return desc;
    }

    public String getUser() {return user;}

    public List<BodyPhoto> getBodyPhotos() {
        return bodyPhotos;
    }

    public List<RecordPhoto> getSlideShowInfo() {
        return null;
    }

}
