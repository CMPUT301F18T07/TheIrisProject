/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */
package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.DeleteProblemTask;

/**
 * DeleteProblemController has methods to allow DeleteProblemActivity
 * to interact with the database to delete problems.
 *
 * @author VinnyLuu
 * @see DeleteProblemTask
 * */
public class DeleteProblemController extends IrisController {

    private String problemID;

    public DeleteProblemController(Intent intent) {
        super(intent);
        model = getModel(intent.getExtras());
    }

    @Override
    Object getModel(Bundle data) {
        problemID = data.getString(Extras.EXTRA_PROBLEM_ID);
        return IrisProjectApplication.getProblemById(problemID);
    }

    /**
     * deleteProblem is a method to asynchronously deletes the given problem
     * once we receive a response from the database, we return a callback
     * with a boolean result either successful or not
     * @param cb callback method
     */
    public Boolean deleteProblem(Callback<Boolean> cb) {

        if (!IrisProjectApplication.isConnectedToInternet()) return false;

        new DeleteProblemTask(new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean res) { cb.onComplete(res); }
            }).execute(problemID);

        return true;

        }

    }
