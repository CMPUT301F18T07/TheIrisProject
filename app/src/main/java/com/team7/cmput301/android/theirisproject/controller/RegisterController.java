/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.model.User.UserType;


/**
 * Controller for making a new User
 *
 * @author anticobalt
 */
public class RegisterController extends IrisController {

    public RegisterController(Intent intent){
        // don't need extra data from intent, so don't do anything
        super(intent);
    }

    @Override
    Object getModel(Bundle data) {
        return null;
    }

    /**
     * Make a User with parameters, then save it
     * @param username inputted username
     * @param email inputted email
     * @param phoneNumber inputted phone number
     * @param type self-described role of user
     */
    public void createUser(String username, String email, String phoneNumber, UserType type){
        User newUser;
        switch (type) {
            case PATIENT:
                newUser = new Patient(username, email, phoneNumber);
                break;
            case CARE_PROVIDER:
                newUser = new CareProvider(username, email, phoneNumber);
                break;
            default:
                newUser = new Patient(username, email, phoneNumber);
                break;
        }
        add(newUser);
    }

    private void add(User user){
        // Adding user to singleton (or directly to DB) goes here
    }

}
