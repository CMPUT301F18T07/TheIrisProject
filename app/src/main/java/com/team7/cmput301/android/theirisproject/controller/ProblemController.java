/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.activity.ViewProblemActivity;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Comment;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.task.AddCommentTask;
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
    private String problemID;

    public ProblemController(Intent intent) {
        super(intent);
        this.problemID = intent.getExtras().getString(ViewProblemActivity.EXTRA_PROBLEM_ID);
        this.model = getModel(intent.getExtras());
    }

    @Override
    Object getModel(Bundle data) {
        return new Problem();
    }

    /**
     * getProblem will invoke a GetProblemTask to
     * populate the model with the correct data from
     * database, once finished it will invoke the callback
     * from the activity
     *
     * @param cb: callback from activity
     * */
    public void getProblem(Callback cb) {
        new GetProblemTask(new Callback<Problem>() {
            @Override
            public void onComplete(Problem res) {
                model = res;
                cb.onComplete(res);
            }
        }).execute(problemID);
    }

    /**
     * addComment will invoke a AddCommentTask to
     * add comment to database and will then make a
     * callback to GetProblemTask, finally will update
     * the activity
     *
     * @param body: comment content
     * @param cb: final callback from activity
     * */
    public void addComment(String body, Callback cb) {
        User user = IrisProjectApplication.getCurrentUser();
        new AddCommentTask(new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean res) {
                if (res) getProblem(cb);
            }
        }).execute(new Comment(problemID, user.getName(), body, user.getType().toString()));
    }

    public List<BodyPhoto> getBodyPhotos() {
        return ((Problem)model).getBodyPhotos();
    }

    public List<Comment> getComments() { return ((Problem)model).getComments(); }

}
