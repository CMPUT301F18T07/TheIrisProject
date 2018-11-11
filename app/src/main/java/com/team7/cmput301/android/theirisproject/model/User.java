/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import io.searchbox.annotations.JestId;

public abstract class User {
    @JestId
    private String _id;

    private String name;
    private String email;
    private String phone;
    private String role;

    public User(String role, String name, String email, String phoneNumber) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.phone = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return _id;
    }

    public void editContact(String username, String email, String phoneNumber) {

    }
}
