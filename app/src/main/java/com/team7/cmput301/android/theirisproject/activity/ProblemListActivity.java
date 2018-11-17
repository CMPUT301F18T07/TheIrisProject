/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.ProblemListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.ProblemListController;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.ProblemList;
import com.team7.cmput301.android.theirisproject.task.Callback;

/**
 * ProblemListActivity shows the problems of a given user through the intent
 * extra "user"
 *
 * @author itstc
 * */
public class ProblemListActivity extends IrisActivity<ProblemList> {

    private static final int ADD_PROBLEM_RESPONSE = 1;

    private ProblemListController controller;
    private ListView problemsView;
    private Boolean doEditProblem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.problem_list_task_bar);
        setSupportActionBar(toolbar);

        problemsView = findViewById(R.id.problem_item_list);
        controller = createController(getIntent());
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        problemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Problem problem = (Problem) problemsView.getItemAtPosition(i);
                if (doEditProblem) {
                    // Edit the problem
                    Intent intent = new Intent(ProblemListActivity.this, EditProblemActivity.class);
                    intent.putExtra(ViewProblemActivity.EXTRA_PROBLEM_ID, problem.getId());
                    startActivity(intent);
                }
                else {
                    // View the problem
                    Intent intent = new Intent(ProblemListActivity.this, ViewProblemActivity.class);
                    intent.putExtra(ViewProblemActivity.EXTRA_PROBLEM_ID, problem.getId());
                    startActivity(intent);
                }

            }
        });

        problemsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // Delete problem being held on
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        // set click listener to AddProblemFloatingButton
        findViewById(R.id.problem_list_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a AddProblemActivity with a requestCode of ADD_PROBLEM_RESPONSE
                Intent intent = new Intent(ProblemListActivity.this, AddProblemActivity.class);
                startActivityForResult(intent, ADD_PROBLEM_RESPONSE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_problem_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.problem_list_action_edit:
                // Set flag so that when user taps on problem, will take user to edit page
                if (doEditProblem){
                    Toast.makeText(ProblemListActivity.this, "Click on Problem to view", Toast.LENGTH_LONG);
                    doEditProblem = false;
                }
                else {
                    Toast.makeText(ProblemListActivity.this, "Click on Problem to edit", Toast.LENGTH_LONG);
                    doEditProblem = true;
                }
                break;
            case R.id.problem_list_action_viewProfile:
                // View a profile
                Toast.makeText(ProblemListActivity.this, "View Profile", Toast.LENGTH_LONG);
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    /**
     * render will update the Activity with the new state provided
     * in the arguments of invoking this method
     *
     * @param state: new state of model
     * @return void
     * */
    public void render(ProblemList state) {
        ProblemList newState = state;
        problemsView.setAdapter(new ProblemListAdapter(this, R.layout.list_problem_item, newState.getProblems()));
    }
}
