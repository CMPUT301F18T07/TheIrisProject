/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.TransferCode;

import java.io.IOException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * DeleteTransferCodeTask first checks if the given code exists in the DB, and if it is,
 * deletes it (the code is now used) and returns the username for the transferring user to
 * successfully log them in
 *
 * @author Jmmxp
 */
public class DeleteTransferCodeTask extends AsyncTask<String, Void, String> {

    private Callback<String> callback;

    public DeleteTransferCodeTask(Callback<String> callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String code = strings[0];

        String query = "{\n" +
                "  \"query\": {\n" +
                "\t\"term\": { \n" +
                "\t\t\"code\": \"" + code + "\" \n" +
                "\t}\n" +
                "  }\n" +
                "}";

        Search search = new Search.Builder(query)
                .addIndex(IrisProjectApplication.INDEX)
                .addType("code")
                .build();


        JestDroidClient client = IrisProjectApplication.getDB();


        try {
            // Search for the inputted code
            SearchResult searchResult = client.execute(search);

            if (!searchResult.isSucceeded()) {
                return null;
            }

            TransferCode transferCode = searchResult.getSourceAsObject(TransferCode.class, true);

            // Return if the search has no hits
            if (transferCode == null) {
                return null;
            }

            // The code exists, we will delete the code and then return the username to log them in as
            Delete delete = new Delete.Builder(transferCode.getId())
                    .index(IrisProjectApplication.INDEX)
                    .type("code")
                    .build();

            JestResult deleteResult = client.execute(delete);
            if (!deleteResult.isSucceeded()) {
                return null;
            }

            return transferCode.getUsername();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String username) {
        super.onPostExecute(username);
        callback.onComplete(username);
    }
}
