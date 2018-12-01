/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;

import java.io.IOException;

import io.searchbox.core.Get;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 * GetTransferCodeTask returns the transfer code for the given user if it already exists,
 * null otherwise
 *
 * @author Jmmxp
 */
public class GetTransferCodeTask extends AsyncTask<String, Void, String> {

    private Callback<String> callback;

    public GetTransferCodeTask(Callback<String> callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String username = strings[0];

        String query = "{\n" +
                "  \"query\": {\n" +
                "\t\"term\": { \n" +
                "\t\t\"username\": \"" + username + "\" \n" +
                "\t}\n" +
                "  }\n" +
                "}";

        Search search = new Search.Builder(query)
                .addIndex(IrisProjectApplication.INDEX)
                .addType("code")
                .build();

        JestDroidClient client = IrisProjectApplication.getDB();

        try {
            SearchResult searchResult = client.execute(search);

            if (!searchResult.isSucceeded()) {
                return null;
            }

            JsonArray arrayHits = searchResult.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits");
            if (arrayHits.size() == 0) {
                return null;
            }

            String code = arrayHits.get(0).getAsJsonObject().get("_source").getAsJsonObject().get("code").getAsString();

            return code;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String code) {
        super.onPostExecute(code);
        callback.onComplete(code);
    }
}
