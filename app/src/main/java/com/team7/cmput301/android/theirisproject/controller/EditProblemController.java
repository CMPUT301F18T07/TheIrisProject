/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;


import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.activity.ViewProblemActivity;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.EditProblemTask;


/**
 * Controller that submits the edited problem to the database
 * Called from EditProblemActivity
 *
 * @author VinnyLuu
 * @see com.team7.cmput301.android.theirisproject.activity.EditProblemActivity
 */
public class EditProblemController extends IrisController {
    String problemID;

    public EditProblemController(Intent intent) {
        super(intent);
        this.problemID = intent.getExtras().getString(ViewProblemActivity.EXTRA_PROBLEM_ID);
        this.model = getModel(intent.getExtras());
    }

    @Override
    Object getModel(Bundle data) {
        return null;
    }

    public void submitProblem(String title, String desc, Callback cb) {
        Problem submitProblem = new Problem(title, desc, IrisProjectApplication.getCurrentUser().getId());
        new EditProblemTask(cb).execute(submitProblem, problemID);
    }

}
