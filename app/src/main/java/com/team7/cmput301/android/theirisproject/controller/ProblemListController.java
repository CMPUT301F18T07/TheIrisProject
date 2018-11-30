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
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.GetProblemListTask;
import com.team7.cmput301.android.theirisproject.model.ProblemList;

/**
 * ProblemListController allows the Activity/Model to interact with each other
 * without explicitly mutating each other. Controller also handles async requests
 * to database to acquire problems
 *
 * @author itstc
 * */
public class ProblemListController extends IrisController<ProblemList> {

    private String userID;

    public ProblemListController(Intent intent) {

        super(intent);
        String intentId = intent.getStringExtra(Extras.EXTRA_USER_ID);

        if (intentId == null){
            this.userID = IrisProjectApplication.getCurrentUser().getId();
        }
        else {
            this.userID = intentId;
        }

        this.model = getModel(intent.getExtras());

    }

    /**
     * getUserProblems is an asynchronous call to make
     * a request to the database, once we get a response
     * our callback to populate the model is called, then
     * the callback from our activity is called
     *
     * @param cb callback function from activity
     * */
    public Boolean getUserProblems(Callback cb) {

        // Assume can't get latest data, and get the local version unconditionally
        Boolean fullSuccess = false;
        cb.onComplete(model);

        switch (IrisProjectApplication.getCurrentUser().getType()) {

            case PATIENT:
                fullSuccess = true;
                break;

            case CARE_PROVIDER:
                if (IrisProjectApplication.isConnectedToInternet()) {
                    new GetProblemListTask(new Callback<ProblemList>() {
                        @Override
                        public void onComplete(ProblemList res) {
                            cb.onComplete(res);
                        }
                    }).execute(userID);
                    fullSuccess = true;
                }
                break;

            default:
                break;

        }

        return fullSuccess;

    }

    public ProblemList getProblems() {
        return model;
    }

    @Override
    ProblemList getModel(Bundle data) {
        return ((Patient) IrisProjectApplication.getUserById(userID)).getProblems();
    }
}
