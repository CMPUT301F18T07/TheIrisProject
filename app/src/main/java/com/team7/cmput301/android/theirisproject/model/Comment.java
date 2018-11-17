/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.Date;

/**
 * A time-stamped message associated with a Problem.
 *
 * @see Problem
 * @author jtfwong
 */
public class Comment {

    private CareProvider author;
    private String title;
    private Date date;
    private String body;

    /* Constructors */

    public void Comment(CareProvider careProvider, String title, Date date, String body) {

    }

    public void Comment(CareProvider careProvider, String title, String body) {

    }

    /* Basic getters */

    public CareProvider getCareProvider() {
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