/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.team7.cmput301.android.theirisproject.PatientListAdapter;
import com.team7.cmput301.android.theirisproject.ProblemListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.controller.PatientListController;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.ProblemList;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.List;

/**
 * PatientListActivity is the screen which shows a Care Provider all their registered Patients.
 * It lets them add a new Patient by clicking the add button.
 */
public class PatientListActivity extends IrisActivity<List<Patient>> {

    private PatientListController controller;
    private ListView patientsView;

    private static final int ADD_PATIENT_RESPONSE = 0;

    @Override
    protected PatientListController createController(Intent intent) {
        return new PatientListController(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        controller = createController(getIntent());
        patientsView = findViewById(R.id.patient_item_list);

        // set click listener to AddProblemFloatingButton
        findViewById(R.id.patient_list_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a AddProblemActivity with a requestCode of ADD_PROBLEM_RESPONSE
                Intent intent = new Intent(PatientListActivity.this, AddPatientActivity.class);
                startActivityForResult(intent, ADD_PATIENT_RESPONSE);
            }
        });
    }
    
    /**
     * render will update the Activity with the new state provided
     * in the arguments of invoking this method
     *
     * @param newState: new state of model
     * @return void
     * */
    public void render(List<Patient> newState) {
        patientsView.setAdapter(new PatientListAdapter(this, R.layout.list_problem_item, newState);
    }

}
