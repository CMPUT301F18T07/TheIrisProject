/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.team7.cmput301.android.theirisproject.controller.ProblemListController;
import com.team7.cmput301.android.theirisproject.model.ProblemList;

public class ProblemListActivity extends IrisActivity<ProblemList> {

    private ProblemListController controller;
    private ListView problemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);
        controller = createController(getIntent());
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

    @Override
    protected ProblemListController createController(Intent intent) {
        return new ProblemListController(intent);
    }

    public void render(ProblemList state) {
        ProblemList newState = (ProblemList) state;
        problemsView.setAdapter(new ProblemListAdapter(this, R.layout.list_problem_item, newState.asList()));
    }
}
