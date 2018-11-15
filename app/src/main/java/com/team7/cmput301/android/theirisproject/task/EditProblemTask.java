/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;

import java.io.IOException;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;


/**
 * EditProblemTask is an AsyncTask that sends a POST request to our
 * database with the edited problem data.
 *
 * @author VinnyLuu
 * */
public class EditProblemTask extends AsyncTask<Object, Void, String> {

    private Callback cb;
    public EditProblemTask(Callback cb) {
        this.cb = cb;
    }

    /**
     * doInBackground will send a request to DB with the edited problem
     * returning a string of the problem id
     * @param params: params[0] containing the new edited Problem.class
     * @param params: params[1] containing the id of the problem to be replaced
     * @return String: id of the edited problem if successful, otherwise null
     * */
    @Override
    protected String doInBackground(Object... params) {
        try {
            Index update = new Index.Builder(params[0]).id(params[1].toString()).index(IrisProjectApplication.INDEX).type("problem").build();
            DocumentResult res = IrisProjectApplication.getDB().execute(update);
            if (res.isSucceeded()) {
                return res.getId();
            }
            else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        cb.onComplete(res);
    }
}
