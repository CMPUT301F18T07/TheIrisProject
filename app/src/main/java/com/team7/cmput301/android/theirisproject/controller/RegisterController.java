/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.model.Profile;


/**
 * Controller for handling user actions and interacting with Profile in LoginActivity
 *
 * @author anticobalt
 */
public class RegisterController extends IrisController {

    Profile new_profile;

    public RegisterController(Intent intent){
        super(intent); // sets getModel() result to this.model
        this.new_profile = (Profile) this.model; // alias for simplicity
    }

    @Override
    Object getModel(Bundle data) {
        // making a new Profile/User, so don't need to fetch model from anywhere
        return new Profile("", "", "");
    }

}
