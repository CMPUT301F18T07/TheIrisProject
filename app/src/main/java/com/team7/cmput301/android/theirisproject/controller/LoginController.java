/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.activity.LoginActivity;
import com.team7.cmput301.android.theirisproject.activity.PatientListActivity;
import com.team7.cmput301.android.theirisproject.activity.ProblemListActivity;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.task.GetUserDataTask;
import com.team7.cmput301.android.theirisproject.task.LoginTask;

/**
 * LoginController helps the activity interact with the database
 * by separating its concern of handling the asynchronous calls
 * to authenticate fields
 *
 * @author itstc
 * @author anticobalt
 * */
public class LoginController extends IrisController<User> {

    public LoginController(Intent intent) {
        super(intent);
    }

    /**
     * loginUser is a method to asynchronously check the database
     * for existing user and authenticate it will trigger a callback
     * when we receive a response with a result boolean success or not
     *
     * @param email User email
     * @param password User password
     * @param cb Callback method
     * */
    public void loginUser(String email, String password, Callback cb) {
        new LoginTask(cb).execute(email);
    }

    @Override
    User getModel(Bundle data) {
        return null;
    }

    /**
     * Get all aggregate data of the User from elasticsearch
     * (e.g. all Problems, Records, photos)
     * and bind it to the current user
     */
    public void fetchAllUserData(Callback callback) {
        User user = IrisProjectApplication.getCurrentUser();
        new GetUserDataTask(callback).execute(user);
    }

    /**
     * Return the Activity that the user is supposed to land in after logging in
     *
     * @return an Activity class
     */
    public Class getStartingActivity() {
        switch (IrisProjectApplication.getCurrentUser().getType()) {
            case PATIENT:
                return ProblemListActivity.class;
            case CARE_PROVIDER:
                return PatientListActivity.class;
            default:
                System.err.println("Unhandled user type: could not start first unique activity.");
                return LoginActivity.class;
        }
    }

}
