/*
 * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.task.AddRecordTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

/**
 * AddRecordActivity is a form to add an activity
 *
 * @author jtfwong
 * */
public class AddRecordActivity extends AppCompatActivity {
    private String problemId;
    private TextView titleField;
    private TextView descField;

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        problemId = getIntent().getStringExtra(ViewProblemActivity.EXTRA_PROBLEM_ID);
        titleField = findViewById(R.id.record_title_edit_text);
        descField = findViewById(R.id.record_description_edit_text);

        submitButton = findViewById(R.id.record_add_problem_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Record submitRecord = new Record(problemId, titleField.getText().toString(), descField.getText().toString());
                new AddRecordTask(new Callback<String>() {
                    @Override
                    public void onComplete(String res) {
                        if (res != null) dispatchRecordActivity(res);
                        else setErrorMessage();
                    }
                }).execute(submitRecord);
            }
        });
    }

    private void setErrorMessage() {
        Toast.makeText(AddRecordActivity.this, "Error making Record!", Toast.LENGTH_LONG).show();
    }

    /**
     * dispatchRecordActivity will goto a view record
     * activity with the given id
     * */
    private void dispatchRecordActivity(String id) {
        Intent intent = new Intent(AddRecordActivity.this, ViewRecordActivity.class);
        intent.putExtra("record_id", id);
        startActivity(intent);
    }

}
