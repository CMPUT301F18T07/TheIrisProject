/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import io.searchbox.annotations.JestId;

/**
 * An abstract user of the app.
 *
 * @author jtfwong
 */
public abstract class User {

    public enum UserType {
        PATIENT, CARE_PROVIDER
    }

    @JestId
    private String _id;
    private String username;
    private String email;
    private String phoneNumber;
    private UserType type;

    public User(String name, String email, String phoneNumber, UserType type) {
        this.username = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    /* Basic setters */

    public void setId(String _id) {
        this._id = _id;
    }

    /* Basic getters */

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public String getId() {
        return _id;
    }

    public UserType getType() {
        return this.type;
    }

    public boolean isUser(String targetId) {
        return _id.equals(targetId);
    }

    /* Advanced setters */

    public void editContact(String username, String email, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}