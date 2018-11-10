/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

public abstract class User {
    public enum UserType {
        PATIENT, CARE_PROVIDER
    }

    private Profile profile;
    private UserType role;

    public User(UserType role, String name, String email, String phoneNumber) {
        this.role = role;
        this.profile = new Profile(name, email, phoneNumber);
    }

    public Profile getContact() {
        return this.profile;
    }

    public UserType getRole() {
        return this.role;
    }

    public void editContact(String username, String email, String phoneNumber) {

    }
}
