/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import io.searchbox.annotations.JestId;

public abstract class User { 
    public enum UserType {
        PATIENT, CARE_PROVIDER
    }

    @JestId
    private String _id;
    private String name;
    private String email;
    private String phoneNumber;
    private UserType type;

    public User(String name, String email, String phoneNumber, UserType type) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
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

    public void editContact(String username, String email, String phoneNumber) {
        this.name = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
