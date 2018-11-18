/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import com.team7.cmput301.android.theirisproject.ImageConverter;
import com.team7.cmput301.android.theirisproject.helper.DateHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private String userId;

    private String title;
    private Date date;
    private String description;
    transient private RecordList records;
    transient private List<Comment> comments = new ArrayList<>();
    transient private List<BodyPhoto> bodyPhotos;

    /* Constructors */

    public Problem(String title, String description, String userId, List<BodyPhoto> bodyPhotos) {
        this(title, description, userId);
        this.bodyPhotos = bodyPhotos;
        this.date = new Date();
    }

    public Problem(String title, String description, String userId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.date = new Date();

    }

    public Problem(String title, String description, String date, String userId) throws ParseException {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.date = DateHelper.parse(date);
    }

    public Problem() {

    }

    /* Basic setters */

    public void setRecords(RecordList records){
        this.records = records;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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


    public String getDate() {
        return DateHelper.format(this.date);

    }

    public String getDescription() {
        return this.description;
    }

    public String getUserId() {return userId;}

    public List<BodyPhoto> getBodyPhotos() {
        return bodyPhotos;
    }

    /* Advanced getters */

    public List<RecordPhoto> getSlideShowInfo() {
        return null;
    }

    public void setBodyPhotos(List<BodyPhoto> photos) {
        bodyPhotos = photos;
    }

}