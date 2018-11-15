/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.searchbox.annotations.JestId;

public class Problem {
    @JestId
    private String _id;

    transient private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CANADA);
    private String title;
    private String user;
    private Date date;
    private RecordList records;
    private List<Comment> comments = new ArrayList<>();
    private String description;
    private List<BodyPhoto> bodyPhotos;

    public Problem(String title, String description, String user, RecordList records, List<BodyPhoto> bodyPhotos) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.records = records;
        this.bodyPhotos = bodyPhotos;
        this.date = new Date();
    }

    public Problem(String title, String description, String user) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.date = new Date();

    }

    public Problem(String title, String description, String date, String user) throws ParseException {
        this.title = title;
        this.description = description;
        this.user = user;
        this.date = dateFormat.parse(date);
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


    public String getDate() {
        return this.dateFormat.format(date);

    }

    public String getDescription() {
        return this.description;
    }


    public String getUser() {return user;}

    public List<BodyPhoto> getBodyPhotos() {
        return bodyPhotos;
    }

    public List<RecordPhoto> getSlideShowInfo() {
        return null;
    }

}