/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import com.team7.cmput301.android.theirisproject.helper.DateHelper;

import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * A time-stamped message associated with a Problem.
 *
 * @see Problem
 * @author jtfwong
 */
public class Comment {

    @JestId
    private String _id;
    private String problemId;
    private String author;
    private String role;
    private Date date;
    private String body;

    /* Constructors */

    public Comment(String problemId, String author, String body, String role) {
        this.problemId = problemId;
        this.author = author;
        this.body = body;
        this.role = role;
        this.date = new Date();
    }

    /* Basic getters */

    public String getId() { return _id; }

    public String getProblemId() { return problemId; }

    public String getRole() { return role; }

    public String getAuthor() {
        return author;
    }
  
    public String getDate() {
        return DateHelper.format(date);
    }

    public String getBody() {
        return body;
    }

}