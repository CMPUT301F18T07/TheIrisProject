/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;


import com.team7.cmput301.android.theirisproject.activity.ViewProblemActivity;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.GetProblemTask;

import java.util.List;


/**
 * Controller that gets the problem from the database
 * Called from ViewProblemActivity
 *
 * @author VinnyLuu
 * @see ViewProblemActivity
 */
public class ProblemController extends IrisController {
    String problemID;

    public ProblemController(Intent intent) {
        super(intent);
        this.problemID = intent.getExtras().getString(ViewProblemActivity.EXTRA_PROBLEM_ID);
        this.model = getModel(intent.getExtras());
    }

    @Override
    Object getModel(Bundle data) {
        return new Problem();
    }

    public void getProblem(Callback cb) {
        new GetProblemTask(new Callback<Problem>() {
            @Override
            public void onComplete(Problem res) {
                res.convertBlobsToBitmaps();
                model = res;
                cb.onComplete(res);
            }
        }).execute(problemID);
    }

    public List<BodyPhoto> getBodyPhotos() {
        return ((Problem)model).getBodyPhotos();
    }

}
