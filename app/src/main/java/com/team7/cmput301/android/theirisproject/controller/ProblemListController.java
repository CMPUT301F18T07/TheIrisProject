/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
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
        this.model = getModel(intent.getExtras());
        String intentId = intent.getStringExtra("user");
        if (intentId == null) this.userID = IrisProjectApplication.getCurrentUser().getId();
        else this.userID = intentId;
    }

    /**
     * getUserProblems is an asynchronous call to make
     * a request to the database, once we get a response
     * our callback to populate the model is called, then
     * the callback from our activity is called
     *
     * @param cb callback function from activity
     * */
    public void getUserProblems(Callback cb) {
        new GetProblemListTask(new Callback<ProblemList>() {
            @Override
            public void onComplete(ProblemList res) {
                model = res;
                cb.onComplete(res);
            }
        }).execute(userID);
    }

    public ProblemList getProblems() {
        return model;
    }

    @Override
    ProblemList getModel(Bundle data) {
        return new ProblemList();
    }
}
