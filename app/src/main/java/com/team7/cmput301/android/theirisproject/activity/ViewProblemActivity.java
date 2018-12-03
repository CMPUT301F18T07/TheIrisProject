/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.CommentListAdapter;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.GetAllGeoLocationsController;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.controller.ProblemController;
import com.team7.cmput301.android.theirisproject.model.Comment;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity that is used to view the problem selected by the user
 * Uses ProblemController to get problem selected from the database
 *
 * @author VinnyLuu
 * @see ProblemController
 */
public class ViewProblemActivity extends IrisActivity<Problem> {

    private ProblemController problemController;
    private GetAllGeoLocationsController getAllGeoLocationsController;
    private String problemId;

    private TextView problemTitle;
    private TextView problemDate;
    private TextView problemDescription;
    private TextView problemLocation;
    //private ViewFlipper problemImages;

    private RecyclerView commentList;
    private CommentListAdapter commentListAdapter;

    private EditText commentBox;
    private Button commentSubmit;

    private Button viewRecordsButton;
    private Button createRecordButton;
    private Button viewSlideshowButton;
    private FloatingActionButton viewAllLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        problemController = (ProblemController) createController(getIntent());

        problemId = getIntent().getStringExtra(Extras.EXTRA_PROBLEM_ID);

        problemTitle = findViewById(R.id.problem_title);
        problemDate = findViewById(R.id.problem_date);
        problemDescription = findViewById(R.id.problem_description);

        commentList = findViewById(R.id.problem_comments);
        inflateCommentList();

        commentBox = findViewById(R.id.problem_comment_box);
        commentSubmit = findViewById(R.id.problem_comment_submit_button);

        viewRecordsButton = findViewById(R.id.view_record_button);
        createRecordButton = findViewById(R.id.create_record_button);
        viewSlideshowButton = findViewById(R.id.slideshow_button);
        viewAllLocations = findViewById(R.id.view_all_locations);

        // Set onclicklistener to view all record locations associated with problem
        viewAllLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchMapActivity();
            }
        });

        // Set onclicklistener to submit comment button
        commentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentBox.getText().length() == 0) setCommentErrorMessage();
                else {
                    Boolean success = problemController.addComment(commentBox.getText().toString(), commentsCallback());
                    if (success) {
                        commentBox.setText("");
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    else showOfflineFatalToast(ViewProblemActivity.this);
                }

            }
        });

        // Set onclicklistener to view records button
        viewRecordsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dispatchViewRecordsActivity(problemId);
            }
        });

        // Set onclick listener to create record button
        createRecordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dispatchCreateRecordActivity(problemId);
            }
        });

        // Set onclick listener to view slideshow button
        viewSlideshowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dispatchProblemSlideshowActivity(problemId);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_problem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // finish activity on back arrow clicked in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.view_problem_edit:
                dispatchEditProblemActivity(problemId);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * dispatchMapActivity will dispatch to MapActivity, calling on allGeoLocationsController to
     * get all the geolocations and record titles for all the records associated to the problem
     * currently on.
     * @see GetAllGeoLocationsController
     */
    private void dispatchMapActivity() {
        getAllGeoLocationsController = new GetAllGeoLocationsController(getIntent());
        getAllGeoLocationsController.getGeolocation(new Callback<List<Object>>() {
            @Override
            public void onComplete(List<Object> res) {
                List<Object> locations = (ArrayList<Object>) res;
                Intent intent = new Intent(ViewProblemActivity.this, MapActivity.class);
                intent.putExtra(Extras.EXTRA_LOCATION, (Serializable) locations.get(0));
                intent.putStringArrayListExtra(Extras.EXTRA_TITLES, (ArrayList<String>) locations.get(1));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        problemController.getProblem(renderCallback());
    }

    /**
     * Printing error message on current activity
     */
    private void setCommentErrorMessage() {
        Toast.makeText(ViewProblemActivity.this, R.string.comment_empty_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected IrisController createController(Intent intent) {
        return new ProblemController(intent);
    }

    private Callback<List<Comment>> commentsCallback() {
        return new Callback<List<Comment>>() {
            @Override
            public void onComplete(List<Comment> res) {
                renderComments(res);
            }
        };
    }

    private Callback<Problem> renderCallback() {
        return new Callback<Problem>() {
            @Override
            public void onComplete(Problem res) {
                render(res);
            }
        };
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
        commentList.setVerticalScrollBarEnabled(true);
        commentListAdapter = new CommentListAdapter(this, new ArrayList<>());
        commentList.setAdapter(commentListAdapter);
    }

    public void renderComments(List<Comment> state) {
        // update the recyclerviews adapters
        if (state.size() != commentListAdapter.getItemCount()) {
            commentListAdapter.setItems(state);
            commentListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * render will update the Activity with the new state provided
     * in the arguments of invoking this method
     *
     * @param state new state of model
     * */
    public void render(Problem state) {
        // update primitive fields
        problemTitle.setText(state.getTitle());
        problemDate.setText(state.getDateAsString());
        problemDescription.setText(state.getDescription());

        renderComments(state.getComments());
    }

    /**
     * dispatchViewRecordsActivity starts view record activity, adding
     * the problem id to intent for retrieval of records.
     * @param id id of the problem
     */
    private void dispatchViewRecordsActivity(String id) {
        Intent intent = new Intent(ViewProblemActivity.this, RecordListActivity.class);
        intent.putExtra(Extras.EXTRA_PROBLEM_ID, id);
        startActivity(intent);
    }

    /**
     * dispatchCreateRecordActivity starts create record activity, adding
     * the problem id to intent for the creation of new record.
     * @param id id of the problem
     */
    private void dispatchCreateRecordActivity(String id) {
        Intent intent = new Intent(ViewProblemActivity.this, AddRecordActivity.class);
        intent.putExtra(Extras.EXTRA_USER_ID, IrisProjectApplication.getCurrentUser().getId());
        intent.putExtra(Extras.EXTRA_PROBLEM_ID, id);
        startActivity(intent);
    }

    /**
     * dispatchProblemSlideshowActivity starts problem slideshow activity, adding
     * the problem id to intent for the view of slideshow.
     * @param id id of the problem
     */
    private void dispatchProblemSlideshowActivity(String id) {
        Intent intent = new Intent(ViewProblemActivity.this, ProblemSlideshowActivity.class);
        intent.putExtra(Extras.EXTRA_PROBLEM_ID, id);
        startActivity(intent);
    }

    /**
     * dispatchEditProblemActivity starts edit problem activity, adding
     * the problem id to intent for the editing of the problem.
     * @param id id of the problem
     */
    private void dispatchEditProblemActivity(String id) {
        Intent intent = new Intent(ViewProblemActivity.this, EditProblemActivity.class);
        intent.putExtra(Extras.EXTRA_PROBLEM_ID, id);
        startActivity(intent);
    }
}
