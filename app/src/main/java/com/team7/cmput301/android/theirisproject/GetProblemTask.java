/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.model.Problem;

import java.io.IOException;

import io.searchbox.core.Get;

/**
 * GetProblemTask is a function that asynchronously retrieves data from
 * our Elasticsearch database. By giving a problem _id, our JestClient
 * will send a GET request to database in which we will populate the Problem model
 * with the response
 * */
public class GetProblemTask extends AsyncTask<String, Void, Problem> {
    /**
     * doInBackground will request a problem based on given index
     * @params String params: [0] is the _id to be given
     * @return String to onPostExecute(String res)
     * */
    @Override
    protected Problem doInBackground(String... params) {
        Problem res = null;
        try {
            // send GET request to our database endpoint ".../problem/params[0]"
            Get get = new Get.Builder(IrisProjectApplication.INDEX, params[0]).type("problem").build();
            // populate our Problem model with database values corresponding to _id
            res = IrisProjectApplication.getDB().execute(get).getSourceAsObject(Problem.class);
            IrisProjectApplication.getProblem().setProblem(res.getTitle(), res.getDescription(), res.getUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * onPostExecute will invoke updateViews for our Problem model once
     * doInBackground has a response from database and populates Problem
     * @param Problem res: our response problem
     * @return void
     * */
    @Override
    protected void onPostExecute(Problem res) {
        super.onPostExecute(res);
        // once our request task is done, invoke updateViews for Problem model
        IrisProjectApplication.getProblem().updateViews();
    }
}
