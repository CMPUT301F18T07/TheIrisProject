/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.AddProblemController;
import com.team7.cmput301.android.theirisproject.task.Callback;


/**
 * AddProblemActivity is a form to add an activity,
 * by click on submit we are sending a Problem object
 * to our database to be stored (handle offline by storing it in local json).
 *
 * @author itstc
 * */
public class AddProblemActivity extends IrisActivity {
    private static final int REQUEST_CAMERA_IMAGE = 1;

    private AddProblemController controller;
    private TextView name;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controller = createController(getIntent());
        name = findViewById(R.id.problem_title_edit_text);
        desc = findViewById(R.id.problem_description_edit_text);

        // set click listener to submit button
        findViewById(R.id.problem_submit_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // send new problem to database, callback has result true if successful, else false
                Boolean submitted = controller.submitProblem(
                        name.getText().toString(),
                        desc.getText().toString(),
                        new Callback<String>() {
                            @Override
                            public void onComplete(String id) {
                                if (id != null) dispatchViewProblemActivity(id);
                                else Toast.makeText(AddProblemActivity.this,
                                        getString(R.string.add_problem_failure),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                if (!submitted) {
                    showOfflineFatalToast(AddProblemActivity.this);
                }
            }
        });

    }

    // finish activity on back arrow clicked in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected AddProblemController createController(Intent intent) {
        return new AddProblemController(intent);
    }

    private void dispatchViewProblemActivity(String id) {
        // end Activity returning to ProblemListActivity
        Intent intent = new Intent(AddProblemActivity.this, ViewProblemActivity.class);
        intent.putExtra(Extras.EXTRA_PROBLEM_ID, id);
        Toast.makeText(AddProblemActivity.this, getString(R.string.add_problem_success), Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

}
