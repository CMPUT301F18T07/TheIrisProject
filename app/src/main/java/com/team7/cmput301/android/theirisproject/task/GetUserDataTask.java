/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.User;

import java.io.IOException;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Asynchronously gets all of a User's data
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
     * Attach all Patients with a specified Care Provider to its Patient list
     *
     * @param careProvider the bound to Care Provider
     */
    private void bindPatients(CareProvider careProvider) {

        try {

            Search get = new Search.Builder(
                    "{\"query\": {\"match\": {\"careProviderIds\": \"" + careProvider.getId() + "\"}}}")
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("user")
                    .build();

            SearchResult res = IrisProjectApplication.getDB().execute(get);
            List<String> patientIds = res.getSourceAsStringList();

            for (String patientId : patientIds) {

                Patient patient = getPatient(patientId);

                if (patient != null) {
                    careProvider.addPatient(patient);
                }
                else {
                    System.err.println(String.format("Could not get patient %s in %s.",
                            patientId, this.getClass().getSimpleName()));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void bindProblems(Patient user) {
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

}