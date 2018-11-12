/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.model.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * PatientListController is responsible for getting a Care Provider's list of Patients from the database
 *
 * @author Jmmxp
 */
public class PatientListController extends IrisController<List<Patient>> {
    private String userID;

    public PatientListController(Intent intent) {
        super(intent);
        this.model = getModel(intent.getExtras());
        this.userID = intent.getExtras().getString("user");
    }

    @Override
    List<Patient> getModel(Bundle data) {
        return new ArrayList<>();
    }
}
