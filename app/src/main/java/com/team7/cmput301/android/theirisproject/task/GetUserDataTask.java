/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.ProblemList;
import com.team7.cmput301.android.theirisproject.model.User;

import java.io.IOException;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Asynchronously gets all of a User's data.
 * Uses nested calls to achieve this (e.g. getting one Patient will get all its Problems,
 * which will get all those Problems' Records, etc)
 *
 * @author anticobalt
 */
public class GetUserDataTask extends AsyncTask<User, Void, User> {

    private Callback cb;

    public GetUserDataTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected User doInBackground(User... users) {

        User user = users[0];

        switch (user.getType()) {
            case PATIENT:
                bindProblems((Patient) user);
                break;
            case CARE_PROVIDER:
                bindPatients((CareProvider) user);
                break;
            default:
                System.err.println(String.format("Unhandled user type in %s.",
                        this.getClass().getSimpleName()));
                break;
        }

        return user;

    }

    /**
     * Attach all Patients (and their proper data) with a specified Care Provider to its Patient list
     *
     * @param careProvider the bound-to Care Provider
     */
    private void bindPatients(CareProvider careProvider) {

        String query = "{\"query\": {\"match\": {\"careProviderIds\": \"" + careProvider.getId() + "\"}}}";
        SearchResult res = search(query, "user");

        if (res != null) {

            List<Patient> patients = res.getSourceAsObjectList(Patient.class, true);
            careProvider.setPatients(patients);

            for (Patient patient: patients) {
                bindProblems(patient);
            }

        } else printError(CareProvider.class, careProvider.getId());

    }

    /**
     * Attach all Problems (and their aggregate model data) to a specified Patient
     *
     * @param patient the bound-to Patient
     */
    private void bindProblems(Patient patient) {

        String query = "{\"query\": {\"match\": {\"patientID\": \"" + patient.getId() + "\"}}}";
        SearchResult res = search(query, "problem");

        if (res != null) {

            ProblemList problems = new ProblemList(res.getSourceAsObjectList(Problem.class, true));

        }

    }

    private Patient getPatient(String id){

        Patient patient = null;

        try {

            Get get = new Get.Builder(IrisProjectApplication.INDEX, id).type("user").build();
            JestResult result = IrisProjectApplication.getDB().execute(get);
            patient = result.getSourceAsObject(Patient.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return patient;

    }

    /**
     * Execute an elasticsearch query and return its results
     *
     * @param query properly formatted Elasticsearch query
     * @param type The index type the query pertains to
     * @return The raw results
     */
    private SearchResult search(String query, String type) {

        try {

            Search get = new Search.Builder(query)
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType(type)
                    .build();

            return IrisProjectApplication.getDB().execute(get);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Prints a generic error to STDERR
     *
     * @param type The aggregate model's class (e.g. Record)
     * @param id The aggregate models' elasticsearch ID
     */
    private void printError(Class type, String id) {
        System.err.println(String.format("Could not get %s %s in %s.",
                type.getSimpleName(), id, this.getClass().getSimpleName()));
    }

}