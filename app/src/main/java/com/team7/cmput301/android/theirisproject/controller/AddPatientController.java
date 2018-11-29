/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.helper.StringHelper;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.task.AddPatientTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.List;

/**
 * AddPatientController is responsible for executing AddPatientTask which will update the given
 * Care Provider and corresponding Patient to hold each other's IDs in the database
 *
 * @author Jmmxp
 */
public class AddPatientController extends IrisController<Patient> {

    public AddPatientController(Intent intent) {
        super(intent);
    }

    public void addPatient(String patientUsername, Callback<Boolean> callback) {
        AddPatientTask task = new AddPatientTask(callback);
        task.execute(patientUsername, false);
    }

    @Override
    Patient getModel(Bundle data) {
        return null;
    }
}
