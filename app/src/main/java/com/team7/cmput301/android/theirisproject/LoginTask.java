/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.model.Profile;

import java.io.IOException;

import io.searchbox.core.Search;

public class LoginTask extends AsyncTask<String, Void, String> {

    Callback<String> cb;
    public LoginTask(Callback<String> cb) { this.cb = cb;
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
            res = IrisProjectApplication.getDB().execute(get).getFirstHit(Profile.class).id;
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
