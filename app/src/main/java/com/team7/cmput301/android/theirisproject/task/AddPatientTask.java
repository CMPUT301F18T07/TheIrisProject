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
import com.team7.cmput301.android.theirisproject.helper.StringHelper;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;

import java.io.IOException;

import io.searchbox.client.JestResult;
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

        CareProvider careProvider = (CareProvider) IrisProjectApplication.getCurrentUser();

        if (patientEmail == null ) {
            return false;
        }
        Log.i(TAG, patientEmail + " and " + careProvider.getId());

        JestDroidClient client = IrisProjectApplication.getDB();

        // Search for user and get the closest match
        Search get = new Search.Builder("{\"query\": {\"term\": {\"email\": \"" + patientEmail + "\"}}}")
                .addIndex(IrisProjectApplication.INDEX)
                .addType("user")
                .build();

        try {
            SearchResult searchResult = client.execute(get);
            // TODO: add check for when we can't find any patient with that email
            // TODO: add check for adding a patient that's already been added

            if (!searchResult.isSucceeded()) {
                return false;
            }

            Log.i(TAG, searchResult.getJsonString());

            Patient patient = searchResult.getSourceAsObject(Patient.class, true);
            String patientId = patient.getId();
            String careProviderId = careProvider.getId();

            // Add this patient's ID into the list of Patient IDs for the current Care Provider
            // Referred to Jest documentation https://github.com/searchbox-io/Jest/tree/master/jest
            Log.i(TAG, patient.getEmail() + " and " + patient.getId() + patient.getCareProviderIds());

            // Append the current Patient ID onto the Care Provider's existing Patient IDs
            String patientIds = patient.getId();
            String patientIdsConcat = StringHelper.join(careProvider.getPatientIds(), ", ");
            if (!patientIdsConcat.equals("")) {
                patientIds = patientIdsConcat + ", " + patientId;
            }

            boolean success = updateUser(client, "patientIds", patientIds, careProviderId);
            if (!success) {
                return false;
            }
            careProvider.addPatientId(patient.getId());


            // Add the careProvider's ID into the Patient we're currently adding

            // Append the current Care Provider ID onto the Patient's existing Care Provider IDs
            String careProviderIds = careProviderId;
            String careProviderIdsConcat = StringHelper.join(patient.getCareProviderIds(), ", ");
            if (!careProviderIdsConcat.equals("")) {
                careProviderIds = careProviderIdsConcat + ", " + careProviderId;
            }

            success = updateUser(client, "careProviderIds", careProviderIds, patientId);
            if (!success) {
                return false;
            }
            patient.addCareProviderId(careProviderId);


            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }


    /**
     * Update the given userType's list of IDs (representing Patients or Care Providers) into ids
     * using that user's userId
     * @param client
     * @param updateType
     * @param updateIds
     * @param userId
     * @return
     */
    private boolean updateUser(JestDroidClient client, String updateType, String updateIds, String userId) {
        String updateString = "{\n" +
                "    \"doc\" : {\n" +
                "        \"" + updateType + "\" : [\"" + updateIds + "\"]\n" +
                "    }\n" +
                "}";

        Update update = new Update.Builder(updateString).index(IrisProjectApplication.INDEX).type("user").id(userId).build();
        JestResult updateResult = null;
        try {
            updateResult = client.execute(update);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean success = updateResult.isSucceeded();

        if (success) {
            Log.i(TAG, "updateResult succeeded");
        } else {
            Log.i(TAG, "updateResult not succeeded MESSAGE: " + updateResult.getErrorMessage());

        }

        return success;
    }

}
