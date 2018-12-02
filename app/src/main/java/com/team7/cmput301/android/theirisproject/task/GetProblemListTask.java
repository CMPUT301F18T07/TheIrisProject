/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.ProblemList;

import java.io.IOException;
import java.util.Arrays;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.params.Parameters;

/**
 * GetProblemListTask is a function that asynchronously retrieves data from
 * our Elasticsearch database. By giving a problem _id, our JestClient
 * will send a GET request to database in which we will populate the Problem model
 * with the response
 *
 * @author itstc
 * */
public class GetProblemListTask extends AsyncTask<String, Void, ProblemList> {

    private static final String TAG = GetProblemListTask.class.getSimpleName();
    private Callback cb;

    public GetProblemListTask(Callback callback) {
        this.cb = callback;
    }
  
    /**
     * doInBackground will request a problem based on given index
     * @param params [0] is the _id to be given
     * @return String to onPostExecute(String res)
     * */
    @Override
    protected ProblemList doInBackground(String... params) {
        try {
            // send GET request to our database endpoint ".../_search?q=_type:problem&q=user:`params[0]`"
            Search get = new Search.Builder("{"+
                    "    \"query\" : {\n" +
                    "        \"term\" : { \"user\" : \"" + params[0] +"\"}\n" +
                    "    }\n" +
                    "}")
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("problem")
                    .setParameter(Parameters.SIZE, IrisProjectApplication.SIZE)
                    .build();
            // populate our Problem model with database values corresponding to _id
            SearchResult res = IrisProjectApplication.getDB().execute(get);

            return new ProblemList(res.getSourceAsObjectList(Problem.class, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ProblemList();
    }

    /**
     * onPostExecute will invoke updateViews for our ProblemList model once
     * doInBackground has a response from database and populates Problems
     * @param res our response problem
     * */
    @Override
    protected void onPostExecute(ProblemList res) {
        super.onPostExecute(res);
        this.cb.onComplete(res);
    }
}
