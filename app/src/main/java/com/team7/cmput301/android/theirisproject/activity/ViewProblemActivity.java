/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.team7.cmput301.android.theirisproject.BodyPhotoListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.controller.ProblemController;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.Callback;

/**
 * Activity that is used to view the problem selected by the user
 * Uses ProblemController to get problem selected from the database
 *
 * @author VinnyLuu
 * @see ProblemController
 */
public class ViewProblemActivity extends IrisActivity<Problem> {

    public static final String EXTRA_PROBLEM_ID = "com.team7.cmput301.android.theirisproject.extra_problem_id";

    private ProblemController problemController;

    private TextView problemTitle;
    private TextView problemDate;
    private TextView problemDescription;
    private TextView problemLocation;
    //private ViewFlipper problemImages;
  
    private RecyclerView problemImages;
    private BodyPhotoListAdapter bodyPhotoListAdapter;
    private RecyclerView.LayoutManager imageListLayout;

    private Button viewRecordsButton;
    private Button createRecordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
        problemController = (ProblemController) createController(getIntent());

        String problemId = getIntent().getStringExtra(EXTRA_PROBLEM_ID);

        problemTitle = findViewById(R.id.problem_title);
        problemDate = findViewById(R.id.problem_date);
        problemDescription = findViewById(R.id.problem_description);
        problemImages = findViewById(R.id.problem_images);
        //problemImages = findViewById(R.id.viewProblem_viewflipper);

        viewRecordsButton = findViewById(R.id.view_record_button);
        createRecordButton = findViewById(R.id.create_record_button);

        viewRecordsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dispatchViewRecordsActivity(problemId);
            }
        });

        createRecordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dispatchCreateRecordActivity(problemId);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        problemController.getProblem(new Callback<Problem>() {
            @Override
            public void onComplete(Problem res) {
                imageListLayout = new LinearLayoutManager(ViewProblemActivity.this);
                ((LinearLayoutManager) imageListLayout).setOrientation(LinearLayoutManager.HORIZONTAL);
                problemImages.setLayoutManager(imageListLayout);
                bodyPhotoListAdapter = new BodyPhotoListAdapter(problemController.getBodyPhotos(), false);
                problemImages.setAdapter(bodyPhotoListAdapter);
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
        problemDate.setText(newState.getDate().toString());
        problemDescription.setText(newState.getDescription());
    }

    private void dispatchViewRecordsActivity(String id) {
        Intent intent = new Intent(ViewProblemActivity.this, RecordListActivity.class);
        intent.putExtra(EXTRA_PROBLEM_ID, id);
        startActivity(intent);
    }

    private void dispatchCreateRecordActivity(String id) {
        Intent intent = new Intent(ViewProblemActivity.this, AddRecordActivity.class);
        intent.putExtra(EXTRA_PROBLEM_ID, id);
        startActivity(intent);
    }
}
