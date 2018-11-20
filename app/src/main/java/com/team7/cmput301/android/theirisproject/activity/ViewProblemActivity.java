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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.team7.cmput301.android.theirisproject.BodyPhotoListAdapter;
import com.team7.cmput301.android.theirisproject.CommentListAdapter;
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

    private RecyclerView commentList;
    private CommentListAdapter commentListAdapter;

    private EditText commentBox;
    private Button commentSubmit;

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

        commentList = findViewById(R.id.problem_comments);

        commentBox = findViewById(R.id.problem_comment_box);
        commentSubmit = findViewById(R.id.problem_comment_submit_button);

        viewRecordsButton = findViewById(R.id.view_record_button);
        createRecordButton = findViewById(R.id.create_record_button);

        commentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentBox.getText().length() == 0) setCommentErrorMessage();
                else problemController.addComment(commentBox.getText().toString(), updateCallback());
                commentBox.setText("");
            }
        });

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
        problemController.getProblem(onCreateCallback());
    }

    private void setCommentErrorMessage() {
        Toast.makeText(ViewProblemActivity.this, "Comment Field is Empty!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected IrisController createController(Intent intent) {
        return new ProblemController(intent);
    }

    private Callback<Problem> onCreateCallback() {
        return new Callback<Problem>() {
            @Override
            public void onComplete(Problem res) {
                inflateBodyPhotoImages();
                inflateCommentList();
                render(res);
            }
        };
    }

    private Callback<Problem> updateCallback() {
        return new Callback<Problem>() {
            @Override
            public void onComplete(Problem res) {
                render(res);
            }
        };
    }

    /**
     * inflateBodyPhotoImages will setup the listAdapter and
     * layoutManager for the RecyclerView problemImages once we
     * have the problem data in the controller
     * */
    private void inflateBodyPhotoImages() {
        LinearLayoutManager imageListLayout = new LinearLayoutManager(ViewProblemActivity.this);
        imageListLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        problemImages.setLayoutManager(imageListLayout);
        bodyPhotoListAdapter = new BodyPhotoListAdapter(problemController.getBodyPhotos(), false);
        problemImages.setAdapter(bodyPhotoListAdapter);
    }

    /**
     * inflateCommentList will setup the listAdapter and
     * layoutManager for the RecyclerView commentList once we
     * have the problem data in the controller
     * */
    private void inflateCommentList() {
        LinearLayoutManager commentListLayout = new LinearLayoutManager(ViewProblemActivity.this);
        commentListLayout.setOrientation(LinearLayoutManager.VERTICAL);
        commentList.setLayoutManager(commentListLayout);
        commentListAdapter = new CommentListAdapter(problemController.getComments());
        commentList.setAdapter(commentListAdapter);
    }

    public void render(Problem state) {
        Problem newState = state;
        // update primitive fields
        problemTitle.setText(newState.getTitle());
        problemDate.setText(newState.getDate());
        problemDescription.setText(newState.getDescription());

        // update the recyclerviews adapters
        bodyPhotoListAdapter.setItems(problemController.getBodyPhotos());
        commentListAdapter.setItems(problemController.getComments());
        bodyPhotoListAdapter.notifyDataSetChanged();
        commentListAdapter.notifyDataSetChanged();
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
