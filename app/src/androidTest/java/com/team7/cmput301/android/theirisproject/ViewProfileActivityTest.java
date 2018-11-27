/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.ViewProfileActivity;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.Patient;

/**
 * Test for ViewProfileActivity
 * @author VinnyLuu
 */
public class ViewProfileActivityTest extends ActivityInstrumentationTestCase2<ViewProfileActivity> {

    private Solo solo;
    private String patientName = "John Doe";
    private String patientEmail = "johndoe@gmail.com";
    private String patientPhone = "123-456-7890";
    private String patientID = "111";


    public ViewProfileActivityTest() {
        super(ViewProfileActivity.class);
    }

    @Override
    protected void setUp() {
        Patient patient = new Patient(patientName, patientEmail, patientPhone);
        patient.setId(patientID);
        Intent intent = new Intent();
        intent.putExtra("patientID", patient.getId());
        setActivityIntent(intent);
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    public void testActivity() {
        String activityName = ViewProfileActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wring activity found, should be " + activityName, activityName);
    }

    /**
     * Test if patient information is correctly loaded
     */
    public void testCorrectPatientData() {
        // Wait for activity to get Patient information from db
        Timer.sleep(1000);

        // TODO: Make sure when ViewProfileActivity is completed that ids are changed appropriately
//        TextView name = (TextView) solo.getView(R.id.patient_name);
//        TextView email = (TextView) solo.getView(R.id.patient_email);
//        TextView phone = (TextView) solo.getView(R.id.patient_phonenum);
//
//        assertTrue(name.getText().toString().equals(patientName));
//        assertTrue(email.getText().toString().equals(patientEmail));
//        assertTrue(phone.getText().toString().equals(patientPhone));
    }

}
