/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;


import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.AddProblemActivity;
import com.team7.cmput301.android.theirisproject.activity.ProblemListActivity;
import com.team7.cmput301.android.theirisproject.activity.ViewPatientProfileActivity;
import com.team7.cmput301.android.theirisproject.activity.ViewProblemActivity;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.AddProblemTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.text.ParseException;

/**
 * Test for ProblemListActivity(Basically the same for cp but less ui options)
 *
 * @author caboteja
 */


public class ProblemListActivityTest extends ActivityInstrumentationTestCase2<ProblemListActivityTest> {

    private Solo solo;

    //May need to ensure that user is not in db
    public String userName = "Michael";
    public String userEmail = "Michael@gmail.com";
    public String userPhone = "123456789";
    public String patientID = "AWcufH5g_rcORbNUUuGo";
    public String probTitle = "Acne";
    public String probDesc = "big big pimples";
    public String probDate = "2018-19-11T21:25:00";
    public String probID = "456";

    public ProblemListActivityTest() { super(ProblemListActivity.class);
    }

    /**
     * referred to VinnyLuu setup in EditProblemActivityTest
     */
    @Override
    protected void setUp() {
        Patient patient = new Patient (userName, userEmail, userPhone);
        patient.setId(patientID);
        IrisProjectApplication.setCurrentUser(patient);
        Problem problem = null;

        try {
            problem = new Problem(probTitle, probDesc, probDate, patientID);
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
        problem.setID(probID);

        new AddProblemTask(new Callback<String>() {
            @Override
            public void onComplete(String Result) {

            }
        }).execute(problem);

        Intent intent = new Intent();
        intent.putExtra(ViewProblemActivity.EXTRA_PROBLEM_ID, problem.getId());
        setActivityIntent(intent);
        solo = new Solo(getInstrumentation(), getActivity());

    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    public void testActivity() {
        String activityName = ProblemListActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + activityName, activityName);
    }

    /**
     * Test buttons in Problem list view
     */

    //currently don't have EditProblemListActivity
    public void TestEditProblems () {
        solo.clickOnButton("Edit Problems");
        solo.assertCurrentActivity("Edit problem selected", EditProblemListActivity.class);
    }

    //currently don't have SearchProblemActivity
    public void TestSearchProblems () {
        solo.clickOnButton("Search");
        solo.assertCurrentActivity("Search problems selected", SearchProblemActivity.class);
    }

    public void TestProfile () {
        solo.clickOnButton("Profile");
        solo.assertCurrentActivity("Profile selected", ViewPatientProfileActivity.class);
    }

    //currently don't have ViewMapOfAllRecordsActivity
    public void TestMap_of_all_records () {
        solo.clickOnButton("Map of all Records");
        solo.assertCurrentActivity("Map of all Records selected", ViewMapOfAllRecordsActivity.class );
    }

    public void TestAddProblem () {
        solo.clickOnButton("Add Problem");
        solo.assertCurrentActivity("Add problem selected", AddProblemActivity.class);
    }

    /**
     * Test problem list info
     */
    public void TestProblemListInfo () {
        //Title
        solo.searchText("Acne");
        //Patient ID
        solo.searchText("AWcufH5g_rcORbNUUuGo");
        //Problem Description
        solo.searchText("big big pimples");
    }

    public void TestSelectProblem () {
        solo.clickInList(1);
        solo.assertCurrentActivity("Problem selected", ViewProblemActivity.class );
    }

    private String getString(int stringId) {
        return getActivity().getString(stringId);
    }


}
