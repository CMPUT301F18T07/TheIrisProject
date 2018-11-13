/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.task.LoginTask;

/**
 * LoginController helps the activity interact with the database
 * by separating its concern of handling the asynchronous calls
 * to authenticate fields
 *
 * @author itstc
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
     * @param email: User email
     * @param password: User password
     * @param cb: Callback method
     * @return void
     * */
    public void loginUser(String email, String password, Callback cb) {
        new LoginTask(cb).execute(email);
    }

    @Override
    User getModel(Bundle data) {
        return null;
    }
}
