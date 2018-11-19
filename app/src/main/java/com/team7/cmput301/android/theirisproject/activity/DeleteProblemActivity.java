/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */
package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.DeleteProblemController;
import com.team7.cmput301.android.theirisproject.task.Callback;

/**
 * DeleteProblemActivity confirms with the user of deletion of selected problem
 * If yes, then problem will be deleted. If no, return back to ProblemListActivity
 *
 * @author VinnyLuu
 * @see DeleteProblemController
 */
public class DeleteProblemActivity extends IrisActivity {

    private DeleteProblemController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_problem);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = (int) (dm.widthPixels * .8);
        int height = (int) (dm.heightPixels * .6);

        getWindow().setLayout(width,height);
        controller = createController(getIntent());

        findViewById(R.id.dontDeleteProblemButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        findViewById(R.id.doDeleteProblemButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.deleteProblem(new Callback<Boolean>() {
                    @Override
                    public void onComplete(Boolean res) {
                        if (res) {
                            setResult(RESULT_OK);
                        }
                        else {
                            setResult(RESULT_FIRST_USER);
                        }
                    }
                });
                finish();
            }
        });
    }

    @Override
    protected DeleteProblemController createController(Intent intent) {
        return new DeleteProblemController(intent);
    }
}