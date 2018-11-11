/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.Callback;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Patient;

import java.io.IOException;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class LoginTask extends AsyncTask<String, Void, String> {

    private Callback cb;
    public LoginTask(Callback cb) { this.cb = cb;
    }

    /**
     * doInBackground is a function that asynchronously requests a search
     * from the database returning a result that matches our email (params[0])
     * @param params: only use params[0] for email
     * @return String: user id
     * */
    @Override
    protected String doInBackground(String... params) {
        String res = "";

        try {
            // HTTP POST to database with given query /_search?q=email:params[0]
            Search get = new Search.Builder("{\"query\": {\"match\": {\"email\": \"" + params[0] + "\"}}}")
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("user")
                    .build();
            // TODO: We make it a patient for now because User.class can't be instantiated...
            SearchResult.Hit<Patient, Void> response = IrisProjectApplication.getDB().execute(get).getFirstHit(Patient.class);
            // check if the hit is equal to email entered then return a result
            if(response.source.getEmail().equals(params[0])) {
                IrisProjectApplication.setCurrentUser(response.source);
                return response.id;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        cb.onComplete(s);
    }
}
