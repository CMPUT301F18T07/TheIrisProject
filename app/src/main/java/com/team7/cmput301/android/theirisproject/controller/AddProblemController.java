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

    private String userId = IrisProjectApplication.getCurrentUser().getId();

    public AddProblemController(Intent intent) {
        super(intent);
    }

    /**
     * submitProblem is a method to asynchronously submit our problem
     * once we receive a response from the database, we return a callback
     * with a boolean result either successful or not
     *
     * @param title Problem title
     * @param desc Problem description
     * @param cb callback method
     * */
    public Boolean submitProblem(String title, String desc, Callback<String> cb) {

        Problem submitProblem = new Problem(title, desc, IrisProjectApplication.getCurrentUser().getId());
        IrisProjectApplication.addProblemToCache(submitProblem);

        if (IrisProjectApplication.isConnectedToInternet()) {

            // add problem to our database
            new AddProblemTask(new Callback<String>() {
                @Override
                public void onComplete(String result) {
                    submitProblem.setId(result);
                    cb.onComplete(result);
                }
            }).execute(submitProblem);
            return true;

        } else {
            IrisProjectApplication.putInUpdateQueue(submitProblem);
            return false;
        }

    }

    @Override
    Problem getModel(Bundle data) {
        return null;
    }

}
