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
import com.team7.cmput301.android.theirisproject.activity.ViewProblemActivity;
import com.team7.cmput301.android.theirisproject.model.Comment;
import com.team7.cmput301.android.theirisproject.model.Contact;
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
public class ProblemController extends IrisController<Problem> {
    private String problemID;

    public ProblemController(Intent intent) {
        super(intent);
        this.problemID = intent.getExtras().getString(Extras.EXTRA_PROBLEM_ID);
        this.model = getModel(intent.getExtras());
    }

    @Override
    Problem getModel(Bundle data) {
        Problem cachedModel = IrisProjectApplication.getProblemById(problemID);
        if (cachedModel != null) return cachedModel;
        return new Problem();
    }

    public Problem getModelProblem() {
        return model;
    }

    /**
     * getProblem will invoke a GetProblemTask to
     * populate the model with the correct data from
     * database, once finished it will invoke the callback
     * from the activity
     *
     * @param cb callback from activity
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

    public void queryComments(Callback cb) {
        new GetProblemTask.GetCommentTask(new Callback<List<Comment>>() {
            @Override
            public void onComplete(List<Comment> res) {
                model.asyncSetComments(res);
                cb.onComplete(model.getComments());
            }
        }).execute(problemID);
    }

    /**
     * addComment will invoke a AddCommentTask to
     * add comment to database and will then make a
     * callback to GetProblemTask, finally will update
     * the activity
     *
     * @param body comment content
     * @param cb final callback from activity
     * */
    public void addComment(String body, Callback cb) {
        User user = IrisProjectApplication.getCurrentUser();
        Contact contact = new Contact(user.getUsername(), user.getPhone(), user.getEmail());
        Comment newComment = new Comment(problemID, contact, body, user.getType());
        model.addComment(newComment);
        cb.onComplete(getComments());

        // update database and pull new comments if found
        new AddCommentTask(new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean res) {
                if (res) queryComments(cb);
            }
        }).execute(newComment);
    }

    public List<Comment> getComments() { return model.getComments(); }

}
