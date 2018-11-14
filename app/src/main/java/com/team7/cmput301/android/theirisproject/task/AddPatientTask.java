/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;

import java.io.IOException;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;

/**
 * AddPatientTask takes in two parameters, the Patient's e-mail that the Care Provider inputted
 * and the Care Provider's ID.
 *
 * If the given email is not a registered email, task returns false onCompletion
 * Otherwise, the Patient's ID is added to the Care Provider's list of Patients and vice versa.
 *
 * @author Jmmxp
 */
public class AddPatientTask extends AsyncTask<String, Void, Boolean> {

    private static final String TAG = AddPatientTask.class.getSimpleName();

    @Override
    protected Boolean doInBackground(String... strings) {
        String patientEmail = strings[0];
        String careProviderId = strings[1];
        if (patientEmail == null || careProviderId == null) {
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
            // TODO: add check for when the result doesn't even contain any hits

            // If search succeeded, let's
            if (!searchResult.isSucceeded()) {
                return false;
            }

            Log.i(TAG, searchResult.getJsonString());

            Patient patient = searchResult.getSourceAsObject(Patient.class, true);

            // Add this patient's ID into the list of Patient IDs for the current Care Provider
            // Referred to Jest documentation https://github.com/searchbox-io/Jest/tree/master/jest
            String patientId = patient.getId();
            String script = "{\n" +
                    "    \"script\" : \"ctx._source.patients += patient\",\n" +
                    "    \"params\" : {\n" +
                    "        \"patient\" : \"" + patientId + "\"\n" +
                    "    }\n" +
                    "}";
            Update update = new Update.Builder(script).index(IrisProjectApplication.INDEX).type("user").id(careProviderId).build();
            JestResult updateResult = client.execute(update);
            if (!updateResult.isSucceeded()) {
                return false;
            }

            // Add the careProvider's ID into the Patient we're currently looking at


        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }
}
