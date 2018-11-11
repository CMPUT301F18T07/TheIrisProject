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
import com.team7.cmput301.android.theirisproject.model.ProblemList;

import android.util.Log;

import java.io.IOException;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * GetProblemTask is a function that asynchronously retrieves data from
 * our Elasticsearch database. By giving a problem _id, our JestClient
 * will send a GET request to database in which we will populate the Problem model
 * with the response
 *
 * @author itstc
 * */
public class GetProblemListTask extends AsyncTask<String, Void, SearchResult> {
    Callback cb;

    public GetProblemListTask(Callback callback) {
        this.cb = callback;
    }
  
    /**
     * doInBackground will request a problem based on given index
     * @params String params: [0] is the _id to be given
     * @return String to onPostExecute(String res)
     * */
    @Override
    protected SearchResult doInBackground(String... params) {
        SearchResult res = null;
        try {
            // send GET request to our database endpoint ".../_search?q=_type:problem&q=user:`params[0]`"
            Search get = new Search.Builder("{\"query\": {\"term\": {\"user\": \"" + params[0] + "\"}}}")
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("problem")
                    .build();
            // populate our Problem model with database values corresponding to _id
            res = IrisProjectApplication.getDB().execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * onPostExecute will invoke callback once
     * doInBackground has a response from database and populates Problem
     * @params SearchResult res: our response of problems
     * onPostExecute will invoke updateViews for our Problem model once
     * doInBackground has a response from database and populates Problem
     * @params Problem res: our response problem
     * @return void
     * */
    @Override
    protected void onPostExecute(SearchResult res) {
        super.onPostExecute(res);
        this.cb.onComplete(new ProblemList(res.getSourceAsObjectList(Problem.class, true)));
    }
}
