/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.EditProblemController;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.controller.ProblemController;
import com.team7.cmput301.android.theirisproject.helper.StringHelper;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.Callback;


import java.text.ParseException;
import java.util.Arrays;

/**
 * Activity that is used to edit the problem selected by the user
 * Uses ProblemController to get problem selected from the database
 * and EditProblemController to submit the edited problem to the database
 *
 * @author VinnyLuu
 * @see ProblemController
 * @see EditProblemController
 */
public class EditProblemActivity extends IrisActivity<Problem> {

    private ProblemController problemController;
    private EditProblemController editProblemController;

    private TextView problemTitle;
    private TextView problemDate;
    private TextView problemDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);
        problemController = (ProblemController) createController(getIntent());
        problemTitle = findViewById(R.id.problem_title);
        problemDate = findViewById(R.id.problem_date);
        problemDescription = findViewById(R.id.problem_description);
        editProblemController = new EditProblemController(getIntent());

        // Set onclicklistener to submit button
        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get and submit new fields to the problem
                try {
                    String title = problemTitle.getText().toString();
                    String description = problemDescription.getText().toString();
                    String date = String.valueOf(problemDate.getText());
                    String[] fields = {title, description, date};

                    // Check if all fields are correctly filled
                    if (StringHelper.hasEmptyString(Arrays.asList(fields))) {
                        Toast.makeText(EditProblemActivity.this, R.string.register_incomplete, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editProblemController.submitProblem(
                            problemTitle.getText().toString(),
                            problemDescription.getText().toString(),
                            String.valueOf(problemDate.getText()),
                            new Callback<String>() {
                                @Override
                                public void onComplete(String id) {
                                    if (id != null) dispatchToProblemActivity(id);
                                    else Toast.makeText(EditProblemActivity.this, "Uh oh something went wrong", Toast.LENGTH_LONG).show();
                                }
                            });
                } catch (ParseException e) {
                    Toast.makeText(EditProblemActivity.this, R.string.incorrect_date, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        problemController.getProblem(new Callback<Problem>() {
            @Override
            public void onComplete(Problem res) {
                render(res);
            }
        });
    }

    @Override
    protected IrisController createController(Intent intent) {
        return new ProblemController(intent);
    }

    /**
     * dispatchToProblemActivity will start the ViewProblemActivity on successful editing of problem
     * and end this activity.
     * @param id id of the edited problem
     */
    private void dispatchToProblemActivity(String id) {
        // end Activity returning to ProblemListActivity
        Intent intent = new Intent(EditProblemActivity.this, ViewProblemActivity.class);
        intent.putExtra(Extras.EXTRA_PROBLEM_ID, id);
        Toast.makeText(EditProblemActivity.this, R.string.successful_edit, Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }

    /**
     * render will update the Activity with the new state provided
     * in the arguments of invoking this method
     *
     * @param state new state of model
     * */
    public void render(Problem state) {
        Problem newState = state;
        problemTitle.setText(newState.getTitle());
        problemDate.setText(newState.getDate().toString());
        problemDescription.setText(newState.getDescription());
    }
}
