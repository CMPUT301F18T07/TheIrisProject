/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.model.User.UserType;

import java.io.IOException;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import static com.team7.cmput301.android.theirisproject.model.User.UserType.PATIENT;

public class LoginTask extends AsyncTask<String, Void, Boolean> {

    private static final String TAG = LoginTask.class.getSimpleName();
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
    protected Boolean doInBackground(String... params) {

        try {
            // HTTP POST to database with given query /_search?q=email:params[0]
            Search get = new Search.Builder("{\"query\": {\"term\": {\"email\": \"" + params[0] + "\"}}}")
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("user")
                    .build();

            SearchResult searchResult = IrisProjectApplication.getDB().execute(get);
            if (!searchResult.isSucceeded()) {
                return false;
            }

            JsonArray arrayHits = searchResult.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits");
            if (arrayHits.size() == 0) {
                return false;
            }

            String type = arrayHits.get(0).getAsJsonObject().get("_source").getAsJsonObject().get("type").getAsString();
            Log.i(TAG, "Type of user: " + type);

            // User is only set successfully if there exists a hit (exact email match)
            User user;
            if (type.equals(PATIENT.toString())) {
                user = searchResult.getSourceAsObject(Patient.class, true);
            } else {
                user = searchResult.getSourceAsObject(CareProvider.class, true);
            }

            IrisProjectApplication.setCurrentUser(user);
            return true;

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
