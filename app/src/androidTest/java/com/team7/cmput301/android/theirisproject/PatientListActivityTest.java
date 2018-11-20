///*
// * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
// *
// *
// */
//
//package com.team7.cmput301.android.theirisproject;
//
//import android.content.Intent;
//import android.test.ActivityInstrumentationTestCase2;
//import com.robotium.solo.Solo;
//import com.team7.cmput301.android.theirisproject.activity.PatientListActivity;
//import com.team7.cmput301.android.theirisproject.model.CareProvider;
//import com.team7.cmput301.android.theirisproject.model.Patient;
//import com.team7.cmput301.android.theirisproject.task.AddPatientTask;
//import com.team7.cmput301.android.theirisproject.task.Callback;
//
//
///**
// * Test for PatientListActivity
// *
// * @author caboteja
// */
//public class PatientListActivityTest extends ActivityInstrumentationTestCase2<PatientListActivityTest> {
//
//    private Solo solo;
//
//    //May need to ensure that user is not in db
//    public String cpuserName = "md";
//    public String cpuserEmail = "Md@gmail.com";
//    public String cpuserPhone = "987654321";
//    public String cpID = "AWcugkyj_rcORbNUUuHB";
//
//    public String puserName = "Michael";
//    public String puserEmail = "Michael@gmail.com";
//    public String puserPhone = "123456789";
//    public String patientID = "AWcufH5g_rcORbNUUuGo";
//
//
//    public PatientListActivityTest() {super(PatientListActivity.class);
//    }
//    /**
//     * referred to VinnyLuu setup in EditProblemActivityTest
//     */
//    @Override
//    protected void setUp() {
//        CareProvider careprovider = new CareProvider(cpuserName, cpuserEmail, cpuserPhone);
//        careprovider.setId(cpID);
//        IrisProjectApplication.setCurrentUser(careprovider);
//        Patient patient = new Patient(puserName, puserEmail, puserPhone);
//        patient.setId(patientID);
//
//        new AddPatientTask (new Callback<String>() {
//            @Override
//            public void onComplete(String Result) {
//            }
//        }).execute(puserEmail);
//
//        solo = new Solo(getInstrumentation(), getActivity());
//    }
//
//    /**
//     * Test for patient list info
//     */
//    public void TestPatientListInfo () {
//        //Patient Name
//        solo.searchText("Michael");
//        //Patient email
//        solo.searchText("michael@gmail.com");
//    }
//
//    /**
//     * Test for interactions
//     */
//
//    //currently don't have AddPatientActivity
//    public void TestAddPatient () {
//        solo.clickOnButton("Add Patient");
//        solo.assertCurrentActivity("Add patient selected", AddPatientActivity.class);
//    }
//
//    //currently don't have ViewPatientProblemsActivity
//    public void TestSelectPatient () {
//        solo.clickInList(1);
//        solo.assertCurrentActivity("Patient selected", ViewPatientProblemsActivity.class );
//    }
//    private String getString(int stringId) {
//        return getActivity().getString(stringId);
//    }
//
//
//}
