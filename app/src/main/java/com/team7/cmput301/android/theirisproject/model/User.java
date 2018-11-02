/*
 * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

public abstract class User {
    private Profile profile;
    private String role;

    public void User(String role, String name, String email, String phoneNumber) {

    }

    public Profile getContact() {
        return this.profile;
    }

    public String getRole() {
        return this.role;
    }

    public void editContact(String username, String email, String phoneNumber) {

    }
}
