/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.Date;

/**
 * Activity that is used to edit the problem selected by the user
 * Uses ProblemController to get problem selected from the database
 * and EditProblemController to submit the edited problem to the database.
 *
 * @author VinnyLuu
 * @see ProblemController
 * @see EditProblemController
 */
public class EditProblemActivity extends IrisActivity<Problem> {

    private ProblemController problemController;
    private EditProblemController editProblemController;

    private TextView problemTitle;
    private Button dateButton;
    private TextView problemDescription;

    private int year;
    private int month;
    private int dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);
        problemController = (ProblemController) createController(getIntent());
        problemTitle = findViewById(R.id.problem_title);
        dateButton = findViewById(R.id.date_button);
        problemDescription = findViewById(R.id.problem_description);
        editProblemController = new EditProblemController(getIntent());

        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        // Set onclicklistener to submit button
        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get and submit new fields to the problem
                try {

                    String title = problemTitle.getText().toString();
                    String description = problemDescription.getText().toString();
                    Date date = parseDate();
                    String[] fields = {title, description};

                    // Check if all fields are correctly filled
                    if (StringHelper.hasEmptyString(Arrays.asList(fields))) {
                        Toast.makeText(EditProblemActivity.this,
                                R.string.register_incomplete,
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Boolean submitted = editProblemController.submitProblem(
                            problemTitle.getText().toString(),
                            problemDescription.getText().toString(),
                            date,
                            new Callback<String>() {
                                @Override
                                public void onComplete(String id) {
                                    if (id != null) {
                                        Toast.makeText(EditProblemActivity.this,
                                                R.string.successful_edit,
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(EditProblemActivity.this,
                                                R.string.edit_problem_failure,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    if (!submitted) {
                        showOfflineUploadToast(EditProblemActivity.this);
                    }
                } catch (ParseException e) {
                    Toast.makeText(EditProblemActivity.this,
                            R.string.incorrect_date,
                            Toast.LENGTH_SHORT).show();
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
     * render will update the Activity with the new state provided
     * in the arguments of invoking this method
     *
     * @param state new state of model
     * */
    public void render(Problem state) {

        Problem newState = state;
        problemTitle.setText(newState.getTitle());
        problemDescription.setText(newState.getDescription());

        Calendar cal = Calendar.getInstance();
        cal.setTime(newState.getDate());
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

    }

    protected void showDatePickerDialog(View v){
        DatePickerFragment dateFrag = new DatePickerFragment();
        dateFrag.setDefaults(this, this.year, this.month, this.dayOfMonth);
        dateFrag.show(getSupportFragmentManager(), "datePicker");
    }

    public void setDateInts(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    private Date parseDate() {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        return c.getTime();
    }

}
