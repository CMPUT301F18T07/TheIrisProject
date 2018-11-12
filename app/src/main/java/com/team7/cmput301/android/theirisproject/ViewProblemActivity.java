/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.controller.ProblemController;
import com.team7.cmput301.android.theirisproject.model.Problem;

/**
 * Activity that is used to view the problem selected by the user
 * Uses ProblemController to get problem selected from the database
 *
 * @author VinnyLuu
 * @see ProblemController
 */
public class ViewProblemActivity extends IrisActivity<Problem> {

    private ProblemController problemController;

    private TextView problemTitle;
    private TextView problemDate;
    private TextView problemDescription;
    private ImageView problemImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
        problemController = (ProblemController) createController(getIntent());
    }

    @Override
    protected void onStart() {
        super.onStart();
        problemTitle = findViewById(R.id.problem_title);
        problemDate = findViewById(R.id.problem_date);
        problemDescription = findViewById(R.id.problem_description);
        problemImages = findViewById(R.id.problem_pic);
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

    public void render(Problem state) {
        Problem newState = state;
        problemTitle.setText(newState.getTitle());
        problemDescription.setText(newState.getDescription());
    }
}
