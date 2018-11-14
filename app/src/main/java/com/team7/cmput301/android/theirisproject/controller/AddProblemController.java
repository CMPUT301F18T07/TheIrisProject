/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.AddProblemTask;


/**
 * AddProblemController has methods to allow our AddProblemActivity
 * to interact with the database by POST requesting new problems to it
 *
 * @author itstc
 * */
public class AddProblemController extends IrisController<Problem> {
    public AddProblemController(Intent intent) {
        super(intent);
    }
    /**
     * submitProblem is a method to asynchronously submit our problem
     * once we receive a response from the database, we return a callback
     * with a boolean result either successful or not
     *
     * @param title: Problem title
     * @param desc: Problem description
     * @param cb: callback method
     * @return void
     * */
    public void submitProblem(String title, String desc, Callback cb) {
        Problem submitProblem = new Problem(title, desc, IrisProjectApplication.getCurrentUser().getId());
        new AddProblemTask(cb).execute(submitProblem);
    }

    @Override
    Problem getModel(Bundle data) {
        return null;
    }
}
