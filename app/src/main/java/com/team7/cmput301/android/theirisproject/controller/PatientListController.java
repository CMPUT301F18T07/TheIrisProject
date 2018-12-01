/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.GetPatientListTask;

import java.util.List;

/**
 * PatientListController is responsible for getting a Care Provider's list of Patients from the database
 *
 * @author Jmmxp
 */
public class PatientListController extends IrisController<List<Patient>> {

    private String userID = IrisProjectApplication.getCurrentUser().getId();

    public PatientListController(Intent intent) {
        super(intent);
        this.model = getModel(intent.getExtras());
    }

    public Boolean getPatientsFromDB(Callback<List<Patient>> callback) {

        if (!IrisProjectApplication.isConnectedToInternet()) return false;

        GetPatientListTask task = new GetPatientListTask(new Callback<List<Patient>>() {
            @Override
            public void onComplete(List<Patient> res) {
                model = res;
                callback.onComplete(res);
            }
        });
        task.execute(userID);

        return true;

    }

    public List<Patient> getPatients() {
        return model;
    }

    @Override
    List<Patient> getModel(Bundle data) {
        return ((CareProvider) IrisProjectApplication.getCurrentUser()).getPatients();
    }

}
