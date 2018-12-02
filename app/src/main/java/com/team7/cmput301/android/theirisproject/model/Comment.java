/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import com.team7.cmput301.android.theirisproject.helper.DateHelper;
import com.team7.cmput301.android.theirisproject.model.User.UserType;

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
    private Contact contact;
    private UserType role;
    private Date date;
    private String body;

    /* Constructors */

    public Comment(String problemId, Contact contact, String body, UserType role) {
        this.problemId = problemId;
        this.contact = contact;
        this.body = body;
        this.role = role;
        this.date = new Date();
    }

    /* Basic getters */

    public String getId() { return _id; }

    public String getProblemId() { return problemId; }

    public UserType getRole() { return role; }

    public String getAuthor() {
        return contact.getName();
    }

    public String getEmail() {
        return contact.getEmail();
    }

    public String getPhone() {
        return contact.getPhone();
    }

    public Contact getContact() {
        return contact;
    }

    public String getDate() {
        return DateHelper.format(date);
    }

    public String getBody() {
        return body;
    }

}