/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */
package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

        // Resizing activity to create popup display
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels * .8);
        int height = (int) (dm.heightPixels * .6);
        getWindow().setLayout(width,height);

        controller = createController(getIntent());

        // Set onclicklistener to dont delete button
        findViewById(R.id.dont_delete_problem_button).setOnClickListener(new View.OnClickListener() {
            // Return to calling activity
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // Set onclicklistener to do delete button
        findViewById(R.id.do_delete_problem_button).setOnClickListener(new View.OnClickListener() {
            /* Delete problem from db, Callback's result is a boolean, true if deletion is
             * successful, false otherwise
             */
            @Override
            public void onClick(View v) {
                controller.deleteProblem(new Callback<Boolean>() {
                        @Override
                        public void onComplete(Boolean res) {
                            if (res) {
                                Intent intent = new Intent();
                                intent.putExtra(ProblemListActivity.DELETE_PROBLEM_ID, controller.getProblemID());
                                setResult(RESULT_OK, intent);
                            }
                            else {
                                setResult(RESULT_FIRST_USER);
                                showOfflineFatalToast(DeleteProblemActivity.this);
                            }
                            finish();
                        }
                });
            }
        });
    }

    @Override
    protected DeleteProblemController createController(Intent intent) {
        return new DeleteProblemController(intent);
    }
}
