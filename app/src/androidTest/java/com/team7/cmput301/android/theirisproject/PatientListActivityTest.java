/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.AddPatientDialogFragment;
import com.team7.cmput301.android.theirisproject.activity.PatientListActivity;
import com.team7.cmput301.android.theirisproject.activity.ViewProblemActivity;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.task.AddPatientTask;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.RegisterTask;

import org.junit.Test;


/**
 * Test for PatientListActivity
 *
 * @author caboteja
 * @author Jmmxp
 */
public class PatientListActivityTest extends ActivityInstrumentationTestCase2<PatientListActivity> {

    private Solo solo;

    //May need to ensure that user is not in db
    public String careProviderName = "testCareProvider";
    public String careProviderEmail = "testCareProvider@gmail.com";
    public String careProviderPhone = "987654321";
    public String careProviderId = "testIdCP";

    public String patientName = "testPatient";
    public String patientEmail = "testPatient@gmail.com";
    public String patientPhone = "123456789";
    public String patientId = "testIdPatient";

    private CareProvider careProvider;
    private Patient patient;

    public PatientListActivityTest() {
        super(PatientListActivity.class);
    }

    @Override
    protected void setUp() {
        careProvider = new CareProvider(careProviderName, careProviderEmail, careProviderPhone);
        careProvider.setId(careProviderId);
        IrisProjectApplication.setCurrentUser(careProvider);

        patient = new Patient(patientName, patientEmail, patientPhone);
        patient.setId(patientId);

        Callback<Boolean> callback = new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean res) {
                // don't need to do anything
            }
        };

        new RegisterTask(callback).execute(careProvider);
        new RegisterTask(callback).execute(patient);

        Timer.sleep(1000);

        Intent intent = new Intent();

        intent.putExtra("user", careProviderId);

        setActivityIntent(intent);

        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    public void testActivity() {
        solo.waitForActivity(PatientListActivity.class);
    }

    public void testAddPatient() {
        View addButton = solo.getView(R.id.patient_list_add);
        solo.clickOnView(addButton, true);

        solo.searchText(getString(R.string.add_patient_dialog_title));
        solo.searchText(getString(R.string.add_patient_dialog_hint));

        EditText editText = (EditText) solo.getView(R.id.add_patient_edit_text);
        solo.enterText(editText, patientEmail);

        // Find a way to click on dialog buttons --> these don't have a reference id
    }

    @Test
    public void testSelectPatient () {
        new AddPatientTask(new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean res) {
                // do nothing
            }
        }).execute(patientEmail);

        Timer.sleep(1000);

        solo.searchText(patientName);
        solo.searchText(patientEmail);

        solo.clickInList(0, 0);

        solo.waitForActivity(ViewProblemActivity.class);
    }

    private String getString(int stringId) {
        return getActivity().getString(stringId);
    }


}
