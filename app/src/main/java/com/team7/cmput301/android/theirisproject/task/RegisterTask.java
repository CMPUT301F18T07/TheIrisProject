package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.Callback;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.RegisterActivity;
import com.team7.cmput301.android.theirisproject.model.User;

import java.io.IOException;


import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * RegisterTask is an AsyncTask that asynchronously registers the given user into the
 * database. It is started by RegisterActivity when the user fills in all fields and clicks the
 * register button.
 *
 * @author Jmmxp
 * @see RegisterActivity
 */

public class RegisterTask extends AsyncTask<User, Void, Boolean> {

    private static final String TAG = RegisterTask.class.getSimpleName();
    private Callback<Boolean> callback;

    public RegisterTask(Callback<Boolean> callback) {
        this.callback = callback;
    }

    /**
     * Searches for the user's email and see if they are already registered in the database. If the
     * email is already used, or if the adding of the user to the database fails, this returns false
     * otherwise it returns true.
     */
    @Override
    protected Boolean doInBackground(User... users) {
        User user = users[0];
        if (user == null) {
            return false;
        }

        JestDroidClient client = IrisProjectApplication.getDB();

        SearchResult searchResult;
        Index index = new Index.Builder(user).index(IrisProjectApplication.INDEX).type("user").build();

        try {


            // Search for user and get the closest match
            Search get = new Search.Builder("{\"query\": {\"term\": {\"email\": \"" + users[0].getEmail() + "\"}}}")
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("user")
                    .build();
            searchResult = IrisProjectApplication.getDB().execute(get);

            // Check if closest match is equal to the inputted user email (i.e. if the email already exists in the database)
            if (!searchResult.isSucceeded()) {
                return false;
            }

            JsonArray arrayHits = searchResult.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits");
            // Stop registration if we already have a hit when searching for the email
            if (arrayHits.size() >= 1) {
                return false;
            }

            // Add the user to the database
            DocumentResult registerResult = client.execute(index);
            if (registerResult.isSucceeded()) {
                user.setId(registerResult.getId());
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Returns the result to RegisterActivity (Boolean representing success or not successful) of registering the user
     */
    @Override
    protected void onPostExecute(Boolean registerResult) {
        super.onPostExecute(registerResult);
        callback.onComplete(registerResult);
    }
}
