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
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.EditProblemTask;


import java.text.ParseException;
import java.util.Date;


/**
 * Controller that submits the edited problem to the database
 * Called from EditProblemActivity
 *
 * @author VinnyLuu
 * @see com.team7.cmput301.android.theirisproject.activity.EditProblemActivity
 */
public class EditProblemController extends IrisController<Problem> {

    private String problemID;

    public EditProblemController(Intent intent) {
        super(intent);
        this.model = getModel(intent.getExtras());
    }

    @Override
    Problem getModel(Bundle data) {
        problemID = data.getString(Extras.EXTRA_PROBLEM_ID);
        return IrisProjectApplication.getProblemById(problemID);
    }

    /**
     * submitProblem is a method to asynchronously submit our edited problem
     * once we receive a response from the database, we return a callback
     * with a boolean result either successful or not
     *
     * @param title edited Problem title
     * @param desc edited Problem description
     * @param date edited Problem date
     * @param cb callback method
     * */
    public Boolean submitProblem(String title, String desc, Date date, Callback cb) throws ParseException{

        model.setTitle(title);
        model.setDescription(desc);
        model.setDate(date);

        if (IrisProjectApplication.isConnectedToInternet()) {
            Problem submitProblem = new Problem(title, desc, date, IrisProjectApplication.getCurrentUser().getId());
            new EditProblemTask(cb).execute(submitProblem, problemID);
            return true;
        } else {
            IrisProjectApplication.putInUpdateQueue(model);
            return false;
        }

    }

}
