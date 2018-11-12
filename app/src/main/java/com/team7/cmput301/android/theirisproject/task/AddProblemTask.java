/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.Callback;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Problem;

import java.io.IOException;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * AddProblemTask is an AsyncTask that sends a POST request to our
 * database with the new problem data. At the end, onPostExecute will
 * either return true/false to the callback
 *
 * @author itstc
 * */
public class AddProblemTask extends AsyncTask<Problem, Void, Boolean> {

    private Callback cb;
    public AddProblemTask(Callback cb) {
        this.cb = cb;
    }

    /**
     * doInBackground will send a request to DB with given new problem
     * returning a boolean for its status
     * @param params: use only params[0] containing a new Problem.class
     * @return Boolean: true if successful, else false
     * */
    @Override
    protected Boolean doInBackground(Problem... params) {
        try {
            Index post = new Index.Builder(params[0]).index(IrisProjectApplication.INDEX).type("problem").build();
            DocumentResult res = IrisProjectApplication.getDB().execute(post);
            return res.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean res) {
        super.onPostExecute(res);
        cb.onComplete(res);
    }
}
