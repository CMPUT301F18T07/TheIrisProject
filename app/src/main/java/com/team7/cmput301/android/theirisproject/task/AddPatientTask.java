/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.helper.StringHelper;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import io.searchbox.params.Parameters;

/**
 * AddPatientTask takes in two parameters, the Patient's e-mail and whether or not we are
 * adding the Patient by importing a Contact or not
 *
 * If the given username is not a registered user, task returns false onCompletion
 * Otherwise, the Patient's ID is added to the Care Provider's list of Patients and vice versa.
 *
 * @author Jmmxp
 */
public class AddPatientTask extends AsyncTask<Object, Void, Boolean> {

    private static final String TAG = AddPatientTask.class.getSimpleName();

    private Callback<Boolean> callback;

    public AddPatientTask(Callback<Boolean> callback) {
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        String addCode = (String) objects[0];
        boolean isImport = false;
        if (objects[1] != null) {
            isImport = (boolean) objects[1];
        }

        CareProvider careProvider = (CareProvider) IrisProjectApplication.getCurrentUser();

        if (addCode == null) {
            return false;
        }

        JestDroidClient client = IrisProjectApplication.getDB();

        // Search for user and get the closest match
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": [\n" +
                "    \t{ \"term\": { \"addCode\": \"" + addCode + "\" }},\n" +
                "    \t{ \"term\": { \"type\": \"PATIENT\" }}\n" +
                "\t  ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Search get = new Search.Builder(query)
                .addIndex(IrisProjectApplication.INDEX)
                .addType("user")
                .setParameter(Parameters.SIZE, IrisProjectApplication.SIZE)
                .build();

        try {
            SearchResult searchResult = client.execute(get);

            if (!searchResult.isSucceeded()) {
                return false;
            }

            JsonArray arrayHits = searchResult.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits");
            // No users found
            if (arrayHits.size() == 0) {
                return false;
            }

            Patient patient = searchResult.getSourceAsObject(Patient.class, true);

            // If the CP already has this patient, do not add again
            if (careProvider.getPatientIds().contains(patient.getId())) {
                return false;
            }

            if (!isImport) {
                careProvider.addPatient(patient);
            }

            String patientId = patient.getId();
            String careProviderId = careProvider.getId();

            // Add this patient's ID into the list of Patient IDs for the current Care Provider
            // Referred to Jest documentation https://github.com/searchbox-io/Jest/tree/master/jest

            // Append the current Patient ID onto the Care Provider's existing Patient IDs
            String patientIds;

            List<String> patientIdList = new ArrayList<>(careProvider.getPatientIds());
            StringHelper.addQuotations(patientIdList);

            String patientIdsConcat = StringHelper.join(patientIdList, ", ");
            if (!patientIdsConcat.equals("")) {
                patientIds = patientIdsConcat + ", " + "\"" + patientId + "\"";
            } else {
                patientIds = "\"" + patientId + "\"";
            }

            boolean success = updateUser(client, "patientIds", patientIds, careProviderId);
            if (!success) {
                return false;
            }
            careProvider.addPatientId(patient.getId());


            // Add the careProvider's ID into the Patient we're currently adding

            // Append the current Care Provider ID onto the Patient's existing Care Provider IDs
            List<String> careProviderIdList = new ArrayList<>(patient.getCareProviderIds());
            StringHelper.addQuotations(careProviderIdList);

            String careProviderIds;

            String careProviderIdsConcat = StringHelper.join(careProviderIdList, ", ");
            if (!careProviderIdsConcat.equals("")) {
                careProviderIds = careProviderIdsConcat + ", " + "\"" + careProviderId + "\"";
            } else {
                careProviderIds = "\"" + careProviderId + "\"";
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
        System.out.println("updateIds is " + updateIds);
        String updateString = "{\n" +
                "    \"doc\" : {\n" +
                "        \"" + updateType + "\" : [" + updateIds + "]\n" +
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

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        callback.onComplete(aBoolean);
    }
}
