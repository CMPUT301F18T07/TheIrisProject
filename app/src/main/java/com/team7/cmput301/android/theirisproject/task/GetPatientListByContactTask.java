/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.helper.StringHelper;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
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
        List<String> emails = new ArrayList<>();
        List<String> phones = new ArrayList<>();
        List<String> registeredPatients = ((CareProvider) IrisProjectApplication.getCurrentUser()).getPatientIds();

        for (Contact contact : contacts) {
            emails.add(contact.getEmail());
            phones.add(contact.getPhone());
        }

        // For every contact, check if the contact's phone or email is a registered patient in the DB
        // Also make sure they are a Patient, and the CP does not already have them registered

        // WHERE type = "PATIENT" AND (email = contactEmail OR email = contactPhone)
        //                "    \t\t\t{ \"terms\": { \"email\": " + StringHelper.generateJSONArrayString(emails) + " }},\n" +
        //                "    \t\t\t{ \"terms\": { \"phoneNumber\": " + StringHelper.generateJSONArrayString(phones) + " }}\n" +
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": [\n" +
                "    \t{ \"term\": { \"type\": \"PATIENT\" }},\n" +
                "    \t{ \"bool\": {\n" +
                "    \t\t\"should\": [\n" +
                "    \t\t\t{ \"terms\": { \"email\": " + StringHelper.generateJSONArrayString(emails) + " }},\n" +
                "    \t\t\t{ \"terms\": { \"phoneNumber\": " + StringHelper.generateJSONArrayString(phones) + " }}\n" +
                "    \t\t]\n" +
                "    \t}}\n" +
                "\t  ],\n" +
                "\t  \"must_not\": {\n" +
                "\t\t\"terms\": { \"_id\": " + StringHelper.generateJSONArrayString(registeredPatients) + " }\n" +
                "\t  }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        System.out.println("query is " + query);

        Search get = new Search.Builder(query)
                .addIndex(IrisProjectApplication.INDEX)
                .addType("user")
                .build();
        try {
            SearchResult res = IrisProjectApplication.getDB().execute(get);

            if (res.isSucceeded()) {
                return res.getSourceAsObjectList(Patient.class, true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Patient> patients) {
        super.onPostExecute(patients);
        callback.onComplete(patients);
    }
}
