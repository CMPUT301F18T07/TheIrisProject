/*
 * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.Date;

public class Comment {
    private CareProvider author;
    private String title;
    private Date date;
    private String body;

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

    public void Comment(CareProvider careProvider, String title, Date date, String body) {

    }

    public void Comment(CareProvider careProvider, String title, String body) {

    }
}
