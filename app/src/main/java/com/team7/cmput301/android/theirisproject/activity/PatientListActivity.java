/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.PatientListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.PatientListController;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.List;

/**
 * PatientListActivity is the screen which shows a Care Provider all their registered Patients.
 * It lets them add a new Patient by clicking the add button, which uses AddPatientDialogFragment to help
 * add the Patient by their email
 *
 * @see AddPatientDialogFragment
 * @author Jmmxp
 */
public class PatientListActivity extends IrisActivity<List<Patient>> implements AddPatientDialogFragment.AddPatientDialogListener {

    private CareProvider loggedInCareProvider;
    private PatientListController controller;
    private ListView patientsView;

    private PatientListAdapter adapter;

    @Override
    protected PatientListController createController(Intent intent) {
        return new PatientListController(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        loggedInCareProvider = (CareProvider) IrisProjectApplication.getCurrentUser();

        controller = createController(getIntent());
        patientsView = findViewById(R.id.patient_item_list);
        adapter = new PatientListAdapter(this, R.layout.list_patient_item, loggedInCareProvider.getPatients());
        patientsView.setAdapter(adapter);
        render();

        patientsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Patient patient = (Patient) patientsView.getItemAtPosition(i);

                Intent intent = new Intent(PatientListActivity.this, ProblemListActivity.class);
                intent.putExtra("user", patient.getId());

                startActivity(intent);
            }
        });

        // set click listener to AddProblemFloatingButton
        findViewById(R.id.patient_list_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addPatientDialog = new AddPatientDialogFragment();

                addPatientDialog.show(getSupportFragmentManager(), AddPatientDialogFragment.class.getSimpleName());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        controller.getPatientsFromDB(new Callback<List<Patient>>() {
//            @Override
//            public void onComplete(List<Patient> res) {
//                render(res);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.patient_list_action_view_profile:
                Intent intent = new Intent(this, ViewProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    /**
     * render will update the PatientList view with the current logged in Care Provider's
     * List of Patients
     * */
    public void render() {
        adapter.notifyDataSetChanged();
        patientsView.invalidateViews();
    }

    @Override
    public void onFinishAddPatient(boolean success) {
        System.out.println("Finished adding patient!!!");
        if (success) {
            controller.getPatientsFromDB(new Callback<List<Patient>>() {
                @Override
                public void onComplete(List<Patient> res) {
                    render();
                }
            });
        } else {
            // do nothing, show unsuccess Toast
            Toast.makeText(this, "Couldn't successfully add Patient!", Toast.LENGTH_SHORT).show();
        }
    }

}
