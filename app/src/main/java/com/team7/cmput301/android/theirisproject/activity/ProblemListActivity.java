/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.ProblemListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.ProblemListController;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.ProblemList;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.List;

/**
 * ProblemListActivity shows the problems of a given user through the intent
 * extra "user"
 *
 * @author itstc
 * @see ProblemListController
 * */
public class ProblemListActivity extends IrisActivity<ProblemList> {

    private static final int DELETE_PROBLEM_RESPONSE = 1;
    public static final String DELETE_PROBLEM_ID = "delete_problem_id";

    private ProblemListController controller;
    private ListView problemsView;
    private FloatingActionButton addProblemButton;
    private FloatingActionButton bodyPhotoButton;
    private FloatingActionButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);

        controller = new ProblemListController(getIntent());
        User.UserType type = IrisProjectApplication.getCurrentUser().getType();

        problemsView = findViewById(R.id.problem_item_list);
        addProblemButton = findViewById(R.id.problem_list_add);
        bodyPhotoButton = findViewById(R.id.body_photo_button);

        switch (type) {
            case CARE_PROVIDER:
                addProblemButton.setVisibility(View.GONE);
                bodyPhotoButton.setVisibility(View.GONE);
                break;
            case PATIENT:
                setAddProblemButton();
                setBodyPhotoButton();
                setProblemLongClick();
                break;
            default:
                break;
        }
        setProblemShortClick();
        setLogoutButton();

    }

    private void setProblemShortClick() {
        problemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Problem problem = (Problem) problemsView.getItemAtPosition(i);
                // View the problem
                Intent intent = new Intent(ProblemListActivity.this, ViewProblemActivity.class);
                intent.putExtra(Extras.EXTRA_PROBLEM_ID, problem.getId());
                startActivity(intent);
            }
        });
    }

    private void setProblemLongClick() {
        // Set onitemlongclicklistener to listview of problems
        problemsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // Delete problem being held on
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Problem problem = (Problem) problemsView.getItemAtPosition(position);
                Intent intent = new Intent(ProblemListActivity.this, DeleteProblemActivity.class);
                intent.putExtra(Extras.EXTRA_PROBLEM_ID, problem.getId());
                startActivityForResult(intent, DELETE_PROBLEM_RESPONSE);
                return true;
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
            case R.id.patient_list_action_view_profile:
                dispatchViewProfileActivity();
                break;
            case R.id.problem_list_action_search:
                if (IrisProjectApplication.isConnectedToInternet()) dispatchToSearchActivity();
                else Toast.makeText(ProblemListActivity.this, R.string.search_offline_message, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
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
     * @param state new state of model
     * */
    public void render(ProblemList state) {
        ProblemList newState = state;
        problemsView.setAdapter(new ProblemListAdapter(this, R.layout.list_problem_item, newState.getProblems()));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DELETE_PROBLEM_RESPONSE) {
            Log.d("Iris", "DELETE RESPONSE");
            // On return from DeleteProblemActivity, check the result of the activity for status of deletion
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(ProblemListActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_OK) {
                Toast.makeText(ProblemListActivity.this, "Problem has been deleted", Toast.LENGTH_SHORT).show();
                controller.getProblems().remove(controller.getProblems().getProblemById(data.getExtras().getString(DELETE_PROBLEM_ID)));
                render(controller.getProblems());
            }
            else {
                Toast.makeText(ProblemListActivity.this, "Can not delete problem", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setAddProblemButton() {
        // set click listener to AddProblemFloatingButton
        addProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a AddProblemActivity with a requestCode of ADD_PROBLEM_RESPONSE
                Intent intent = new Intent(ProblemListActivity.this, AddProblemActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setBodyPhotoButton() {
        bodyPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProblemListActivity.this, BodyPhotoListActivity.class);
                intent.putExtra(Extras.EXTRA_BODYPHOTO_USER, controller.getUserId());
                startActivity(intent);
            }
        });
    }

    private void dispatchToSearchActivity() {
        Intent intent = new Intent(ProblemListActivity.this, SearchActivity.class);
        intent.putExtra(Extras.EXTRA_USER_ID, controller.getUserId());
        startActivity(intent);
    }

    private void dispatchViewProfileActivity() {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }

    private void setLogoutButton() {
        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IrisProjectApplication.logoutCurrentUser();
                finish();
                startActivity(new Intent(ProblemListActivity.this, LoginActivity.class));
            }
        });
    }

}
