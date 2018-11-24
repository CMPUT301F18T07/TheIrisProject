/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;


import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.AddProblemActivity;
import com.team7.cmput301.android.theirisproject.activity.DeleteProblemActivity;
import com.team7.cmput301.android.theirisproject.activity.EditProblemActivity;
import com.team7.cmput301.android.theirisproject.activity.ProblemListActivity;
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
 * @author VinnyLuu
 */


public class ProblemListActivityTest extends ActivityInstrumentationTestCase2<ProblemListActivity> {

    private Solo solo;

    //May need to ensure that user is not in db
    private String userName = "John Doe";
    private String userEmail = "notjohn@gmail.com";
    private String userPhone = "123456789";
    private String patientID = "03";
    private String probTitle = "Acne";
    private String probDesc = "big big pimples";
    private String probDate = "2018-09-01T18:30:00";
    private String probID = "456";
    private Problem problem;

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
        try {
            problem = new Problem(probTitle, probDesc, probDate, patientID);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        problem.setID(probID);

        new AddProblemTask(new Callback<String>() {
            @Override
            public void onComplete(String Result) {

            }
        }).execute(problem);

        Intent intent = new Intent();
        intent.putExtra("user", patient.getId());
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
//    public void testEditProblems () {
//        solo.clickOnButton("Edit Problems");
//        solo.assertCurrentActivity("Edit problem selected", EditProblemListActivity.class);
//    }

    //currently don't have SearchProblemActivity
//    public void testSearchProblems () {
//        solo.clickOnButton("Search");
//        solo.assertCurrentActivity("Search problems selected", SearchProblemActivity.class);
//    }

//    public void testProfile () {
//        solo.clickOnMenuItem("Profile");
//        solo.assertCurrentActivity("Profile selected", ViewProfileActivity.class);
//    }

    //currently don't have ViewMapOfAllRecordsActivity
//    public void TestMap_of_all_records () {
//        solo.clickOnButton("Map of all Records");
//        solo.assertCurrentActivity("Map of all Records selected", ViewMapOfAllRecordsActivity.class );
//    }

    /**
     * Test if AddProblemActivity starts when Add problem button is clicked
     */
    public void testAddProblem () {
        View addproblem = solo.getView(R.id.problem_list_add);
        solo.clickOnView(addproblem);
        solo.waitForActivity(AddProblemActivity.class);
        String activityName = AddProblemActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity!", activityName);
    }

    /**
     * Test problem list info
     */
    public void testProblemListInfo () {
        //Title
        assertTrue(solo.searchText(probTitle));
        //Patient ID
        assertTrue(solo.searchText(probID));
        //Problem Description
        assertTrue(solo.searchText(probDesc));
    }

    /**
     * Test if ViewProblemActivity starts and displays correct information when problem is clicked
     */
    public void testSelectProblem () {
        solo.clickInList(1);
        solo.waitForActivity(ViewProblemActivity.class);
        String activityName = ViewProblemActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity!", activityName);

        TextView problemTitle = (TextView) solo.getView(R.id.problem_title);
        TextView problemDate = (TextView) solo.getView(R.id.problem_date);
        TextView problemDescription = (TextView) solo.getView(R.id.problem_description);

        assertTrue(problemTitle.getText().toString().equals(probTitle));
        assertTrue(problemDate.getText().toString().equals(probDate));
        assertTrue(problemDescription.getText().toString().equals(probDesc));
    }

    /**
     * Test if EditProblemActivity starts and displays correct information when
     * edit problem button is clicked and then a problem is clicked
     */
    public void testEditProblem() {
        solo.clickOnView(solo.getView(R.id.problem_list_action_edit));
        solo.clickInList(1);
        solo.waitForActivity(EditProblemActivity.class);
        String activityName = EditProblemActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity!", activityName);

        EditText title = (EditText) solo.getView(R.id.problem_title);
        EditText desc = (EditText) solo.getView(R.id.problem_description);
        EditText date = (EditText) solo.getView(R.id.problem_date);

        assertTrue(title.getText().toString().equals(probTitle));
        assertTrue(desc.getText().toString().equals(probDesc));
        assertTrue(date.getText().toString().equals(probDate));

    }

    /**
     * Test if DeleteProblemActivity starts when problem is held down
     */
    public void testDeleteProblem() {
        solo.clickLongInList(1);
        solo.waitForActivity(DeleteProblemActivity.class);
        String activityName = DeleteProblemActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity!", activityName);
    }

    private String getString(int stringId) {
        return getActivity().getString(stringId);
    }


}
