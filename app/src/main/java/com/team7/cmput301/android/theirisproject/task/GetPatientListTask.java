/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.params.SearchType;

/**
 * GetPatientListTask is responsible for taking in a careProviderId and returning a List of Patients
 * representing all Patients that Care Provider has registered.
 *
 * It also uses a Callback method to give the List of Patients to the original calling activity
 * (which calls upon the PatientListController)
 *
 * @author Jmmxp
 */
public class GetPatientListTask extends AsyncTask<String, Void, List<Patient>> {

    private static final String TAG = GetPatientListTask.class.getSimpleName();
    private Callback<List<Patient>> callback;

    public GetPatientListTask(Callback<List<Patient>> callback) {
        this.callback = callback;
    }

    @Override
    protected List<Patient> doInBackground(String... strings) {
        String careProviderId = strings[0];

        try {
            // send GET request to our database endpoint ".../_search?q=_type:user&q=careProviders:'careProivderId'"
            // Note that this finds all matches containing

            // TODO: Ensure that "match" doesn't find any patients that shouldn't be found
            Search get = new Search.Builder("{\"query\": {\"match\": {\"careProviderIds\": \"" + careProviderId + "\"}}}")
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("user")
                    .build();
            // populate our Problem model with database values corresponding to _id
            SearchResult res = IrisProjectApplication.getDB().execute(get);

            Log.i(TAG, res.getJsonString());
            System.out.println(Arrays.toString(res.getSourceAsStringList().toArray()));

            if (res.isSucceeded()) {
                Log.i(TAG, "Getting Patients List Result is succeeded!");
                List<Patient> patients = res.getSourceAsObjectList(Patient.class, true);
                Log.i(TAG, "number of patients: " + patients.size());

                return res.getSourceAsObjectList(Patient.class, true);
            }
            Log.i(TAG, "Getting Patients List Result is FAILED!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Patient> patients) {
        super.onPostExecute(patients);

        callback.onComplete(patients);
    }
}
