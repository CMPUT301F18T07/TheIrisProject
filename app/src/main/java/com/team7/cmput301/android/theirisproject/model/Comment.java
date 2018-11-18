/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

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
    private String title;
    private Date date;
    private String body;

    /* Constructors */

    public Comment(String problemId, String author, String title, Date date, String body) {
        this(problemId, author, title, body);
        this.date = date;
    }

    public Comment(String problemId, String author, String title, String body) {
        this.problemId = problemId;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    /* Basic getters */

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

}