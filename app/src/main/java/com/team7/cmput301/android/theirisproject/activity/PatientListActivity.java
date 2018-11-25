/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.PatientListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.PatientListController;
import com.team7.cmput301.android.theirisproject.helper.Timer;
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

    private static final int PERMISSION_REQUEST_READ_CONTACTS = 0;

    private PatientListController controller;
    private ListView patientsView;

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
        controller.getPatientsFromDB(new Callback<List<Patient>>() {
            @Override
            public void onComplete(List<Patient> res) {
                render(res);
            }
        });
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
            case R.id.patient_list_action_import_contacts:
                checkPermissions();
                return true;
            default:
                return false;
        }
    }

    /**
     * render will update the Activity with the new state provided
     * in the arguments of invoking this method
     *
     * @param newState new state of model
     * */
    public void render(List<Patient> newState) {
        patientsView.setAdapter(new PatientListAdapter(this, R.layout.list_patient_item, newState));
    }

    @Override
    public void onFinishAddPatient(boolean success) {
        System.out.println("Finished adding patient!!!");
        if (success) {
            // Render the List with the new Patient that was added
            Timer.sleep(750);
                    controller.getPatientsFromDB(new Callback<List<Patient>>() {
                @Override
                public void onComplete(List<Patient> res) {
                    render(res);
                }
            });
        } else {
            // do nothing, show unsuccess Toast
            Toast.makeText(this, "Couldn't successfully add Patient!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @return whether or not this Activity has the READ_CONTACTS permission already granted to it
     */
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission isn't granted, request the permission
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS},
                    PERMISSION_REQUEST_READ_CONTACTS);
        } else {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getString(R.string.contact_permissions_granted), Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = new Intent(this, ContactsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, getString(R.string.contact_permissions_not_granted), Toast.LENGTH_SHORT)
                            .show();
                }
                return;
            }
            default: {
                return;
            }
        }
    }

}
