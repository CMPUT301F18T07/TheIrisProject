/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.EditRecordController;
import com.team7.cmput301.android.theirisproject.model.Record;

/**
 * Activity for editing a single Record. Uses the same form as AddRecordActivity.
 *
 * @author anticobalt
 * @see Record
 * @see AddRecordActivity
 */
public class EditRecordActivity extends IrisActivity<Record>{

    private EditRecordController controller;
    private View formPage;
    private EditText titleEditText;
    private EditText descEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_form);

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set back button

        // get Views and Buttons
        // https://stackoverflow.com/a/4787064
        formPage = findViewById(R.id.record_form_layout);
        titleEditText = formPage.findViewById(R.id.record_title_edit_text);
        descEditText = formPage.findViewById(R.id.record_description_edit_text);
        submitButton = formPage.findViewById(R.id.record_submit_button);

        // put Record attributes in Views
        controller = createController(getIntent());
        titleEditText.setText(controller.getRecordTitle());
        descEditText.setText(controller.getRecordDesc());

        setOnClickListeners();

    }

    private void setOnClickListeners() {

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleEditText.getText().toString();
                String desc = descEditText.getText().toString();

                Toast missingFieldsToast = Toast.makeText(
                        EditRecordActivity.this, R.string.register_incomplete, Toast.LENGTH_SHORT);
                Toast onlineFailedToast = Toast.makeText(
                        EditRecordActivity.this, R.string.offline_upload_error, Toast.LENGTH_SHORT);

                // check that all fields filled, then update Record locally and online (if possible)
                if (title.isEmpty()) {
                    missingFieldsToast.show();
                } else {
                    Boolean online = controller.submitRecord(title, desc);
                    if (!online) {
                        onlineFailedToast.show();
                    }
                    finish();
                }

            }
        });

    }

    @Override
    protected EditRecordController createController(Intent intent) {
        return new EditRecordController(intent);
    }

}
