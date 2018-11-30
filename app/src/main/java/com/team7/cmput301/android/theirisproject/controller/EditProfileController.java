/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.task.EditProfileTask;

/**
 * EditProfileController is used by EditProfileActivity to update the User's profile into the DB
 *
 * @author Jmmxp
 */
public class EditProfileController extends IrisController<User> {

    public EditProfileController(Intent intent) {
        super(intent);
        model = getModel(intent.getExtras());
    }

    public String getEmail() {
        return model.getEmail();
    }

    public String getPhone() {
        return model.getPhone();
    }

    public Boolean updateProfile(String newEmailText, String newPhoneText) {

        if (IrisProjectApplication.isConnectedToInternet()) {
            new EditProfileTask().execute(model);
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    User getModel(Bundle data) {
        return IrisProjectApplication.getCurrentUser();
    }

}
