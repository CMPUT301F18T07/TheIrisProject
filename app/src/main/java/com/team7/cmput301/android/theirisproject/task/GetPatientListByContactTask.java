/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Contact;
import com.team7.cmput301.android.theirisproject.model.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Takes in a List of Contacts and returns a List of Patients who either have a phone number or
 * e-mail registered in the app that is also part of the Contacts list
 */
public class GetPatientListByContactTask extends AsyncTask<Contact, Void, List<Patient>> {

    private Callback<List<Patient>> callback;

    public GetPatientListByContactTask(Callback<List<Patient>> callback) {
        this.callback = callback;
    }

    @Override
    protected List<Patient> doInBackground(Contact... contacts) {
        List<Patient> result = new ArrayList<>();

        // For every contact, check if the contact's phone or email is a registered patient in the DB
        // Also make sure they are a Patient
        for (Contact contact : contacts) {
            // WHERE type = "PATIENT" AND (email = contactEmail OR email = contactPhone)
            String query = "{\n" +
                    "  \"query\": {\n" +
                    "    \"bool\": {\n" +
                    "      \"must\": [\n" +
                    "    \t{ \"term\": { \"type\": \"PATIENT\" }},\n" +
                    "    \t{ \"bool\": {\n" +
                    "    \t\t\"should\": [\n" +
                    "    \t\t\t{ \"term\": { \"email\": \"" + contact.getEmail() + "\" }},\n" +
                    "    \t\t\t{ \"term\": { \"phoneNumber\": \"" + contact.getPhone() + "\" }}\n" +
                    "    \t\t]\n" +
                    "    \t}}\n" +
                    "\t  ]\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            Search get = new Search.Builder(query)
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("user")
                    .build();
            try {
                SearchResult res = IrisProjectApplication.getDB().execute(get);

                if (!res.isSucceeded()) {
                    return null;
                }

                result.addAll(res.getSourceAsObjectList(Patient.class, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Patient> patients) {
        super.onPostExecute(patients);
        callback.onComplete(patients);
    }
}
