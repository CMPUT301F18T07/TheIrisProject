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

import io.searchbox.core.Index;

public class AddProblemTask extends AsyncTask<Problem, Void, Boolean> {

    Callback cb;
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
            IrisProjectApplication.getDB().execute(post);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean res) {
        super.onPostExecute(res);
        cb.onComplete(res);
    }
}
