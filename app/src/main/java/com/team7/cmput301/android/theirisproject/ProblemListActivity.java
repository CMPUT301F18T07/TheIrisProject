/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.team7.cmput301.android.theirisproject.controller.ProblemListController;
import com.team7.cmput301.android.theirisproject.model.ProblemList;

public class ProblemListActivity extends IrisActivity<ProblemList> {

    private static final int ADD_PROBLEM_RESPONSE = 1;

    private ProblemListController controller;
    private ListView problemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);
        controller = createController(getIntent());

        findViewById(R.id.problem_list_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a AddProblemActivity with a requestCode of ADD_PROBLEM_RESPONSE
                Intent intent = new Intent(ProblemListActivity.this, AddProblemActivity.class);
                intent.putExtra("user", controller.getUserID());
                startActivityForResult(intent, ADD_PROBLEM_RESPONSE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        problemsView = findViewById(R.id.problem_item_list);
        // fetch user problems from database
        controller.getUserProblems(new Callback<ProblemList>() {
            @Override
            public void onComplete(ProblemList res) {
                render(res);
            }
        });
    }

    /**
     * onActivityResult updates our problem list once we return
     * from an activity that we started by intent with a requestCode
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_PROBLEM_RESPONSE) {
            controller.getUserProblems(new Callback<ProblemList>() {
                @Override
                public void onComplete(ProblemList res) {
                    render(res);
                }
            });
        }
    }

    @Override
    protected ProblemListController createController(Intent intent) {
        return new ProblemListController(intent);
    }

    public void render(ProblemList state) {
        ProblemList newState = state;
        problemsView.setAdapter(new ProblemListAdapter(this, R.layout.list_problem_item, newState.asList()));
    }
}
