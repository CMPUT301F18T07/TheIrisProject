/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;

import java.io.IOException;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class AddPatientTask extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... strings) {
        String patientEmail = strings[0];
        if (patientEmail == null) {
            return false;
        }

        JestDroidClient client = IrisProjectApplication.getDB();

        // Search for user and get the closest match
        Search get = new Search.Builder("{\"query\": {\"term\": {\"email\": \"" + patientEmail + "\"}}}")
                .addIndex(IrisProjectApplication.INDEX)
                .addType("user")
                .build();

        try {
            SearchResult searchResult = client.execute(get);
            if (searchResult.isSucceeded()) {
                Patient patient = searchResult.getSourceAsObject(Patient.class, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }
}
