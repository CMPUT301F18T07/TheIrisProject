/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.EditProblemActivity;

import com.team7.cmput301.android.theirisproject.activity.ViewProblemActivity;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.AddProblemTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.text.ParseException;

/**
 * Test for EditProblemActivity
 *
 * @author VinnyLuu
 */
public class EditProblemActivityTest extends ActivityInstrumentationTestCase2<EditProblemActivity> {

    private Solo solo;
    private String userName = "John Doe";
    private String userEmail = "johndoe@gmail.com";
    private String userPhone = "123-456-7890";
    private String patientID = "111";
    private String probTitle = "ouch";
    private String probDesc = "I'm shot";
    private String probDate = "2018-09-01T18:30:00";
    private String probID = "123";
    public EditProblemActivityTest() {
        super(EditProblemActivity.class);
    }

    @Override
    protected void setUp() {
        Patient patient = new Patient(userName, userEmail, userPhone);
        patient.setId(patientID);
        IrisProjectApplication.setCurrentUser(patient);
        Problem problem = null;
        try {
            problem = new Problem(probTitle, probDesc, probDate , patientID);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        problem.setId(probID);


        new AddProblemTask(new Callback<String>()  {
            @Override
            public void onComplete(String result) {
                // Don not need result
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
        String activityName = EditProblemActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + activityName, activityName);
    }

    /**
     * Test if problem details are displayed correctly
     */
    public void testCorrectInitialData() {

        Timer.sleep(1000);
        EditText title = (EditText) solo.getView(R.id.problem_title);
        EditText desc = (EditText) solo.getView(R.id.problem_description);
        EditText date = (EditText) solo.getView(R.id.problem_date);

        assertTrue(title.getText().toString().equals(probTitle));
        assertTrue(desc.getText().toString().equals(probDesc));
        assertTrue(date.getText().toString().equals(probDate));
    }

    /**
     * Test submission when not all fields have been filled out
     */
    public void testFormIncomplete() {
        Button submitButton = (Button) solo.getView(R.id.submit_button);

        // Try registering with all fields removed and empty
        EditText title = (EditText) solo.getView(R.id.problem_title);
        EditText desc = (EditText) solo.getView(R.id.problem_description);
        EditText date = (EditText) solo.getView(R.id.problem_date);

        solo.clearEditText(title);
        solo.clearEditText(desc);
        solo.clearEditText(date);

        solo.clickOnView(submitButton);

        assertTrue(solo.waitForText(getString(R.string.register_incomplete)));
    }

    /**
     * Test if activity handles inappropriate date formats correctly
     */
    public void testWrongDateFormat() {
        Button submitButton = (Button) solo.getView(R.id.submit_button);

        solo.clickOnView(submitButton);

        assertTrue(solo.waitForText(getString(R.string.register_incomplete)));


        EditText title = (EditText) solo.getView(R.id.problem_title);
        EditText desc = (EditText) solo.getView(R.id.problem_description);
        EditText date = (EditText) solo.getView(R.id.problem_date);
        solo.clearEditText(date);

        solo.enterText(title, "Gunshot wound");
        solo.enterText(desc, "It won't stop bleeding");
        solo.enterText(date, "1234");

        solo.clickOnView(submitButton);

        assertTrue(solo.waitForText(getActivity().getString(R.string.incorrect_date)));
    }

    /**
     * Helper that gets the string from the activities' stringId
     * @param stringId
     * @return string of the stringId
     */
    private String getString(int stringId) {
        return getActivity().getString(stringId);
    }

}
