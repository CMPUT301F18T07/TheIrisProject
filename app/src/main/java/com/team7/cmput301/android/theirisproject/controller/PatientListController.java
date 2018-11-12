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

public class PatientListController extends IrisController<List<Patient>> {
    public PatientListController(Intent intent) {
        super(intent);
    }

    @Override
    List<Patient> getModel(Bundle data) {
        return new ArrayList<>();
    }
}
