/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;


import com.team7.cmput301.android.theirisproject.helper.DateHelper;

import java.text.ParseException;

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
    private String user;

    private String title;
    private Date date = new Date();
    private String desc;
    transient private RecordList records = new RecordList();
    transient private List<Comment> comments = new ArrayList<>();

    /* Constructors */

    public Problem(String title, String description, String user) {
        this.title = title;
        this.desc = description;
        this.user = user;
        this.date = new Date();

    }

    public Problem(String title, String description, String date, String user) throws ParseException {
        this.title = title;
        this.desc = description;
        this.user = user;
        this.date = DateHelper.parse(date);
    }

    public Problem() {}

    public Problem(String title, String description, Date date, String user) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.date = date;
    }

    /* Basic setter + adders */

    public void setId(String _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public void setDate(String date) throws ParseException {
        this.date = DateHelper.parse(date);
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void addRecord(Record record) { this.records.add(record); }

    public void setRecords(RecordList records){
        this.records = records;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
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


    public String getDateAsString() {
        return DateHelper.format(this.date);
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getUser() {return user;}

    /* Advanced getters */

    public List<RecordPhoto> getSlideShowInfo() {
        return null;
    }

    /* Advanced setters */

    public synchronized void asyncSetComments(List<Comment> comments) {
        this.comments = comments;
    }

    public synchronized void asyncCopyFields(Problem problem) {
        this._id =  problem.getId();
        this.title = problem.getTitle();
        this.desc = problem.getDescription();
        try {
            this.date = DateHelper.parse(problem.getDateAsString());
        } catch (ParseException e) {
            this.date = new Date();
        }
    }
}