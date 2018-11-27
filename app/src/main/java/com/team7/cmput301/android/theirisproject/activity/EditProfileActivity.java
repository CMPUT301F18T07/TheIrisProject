/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.EditProfileController;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.model.User;

import java.util.Arrays;

import static com.team7.cmput301.android.theirisproject.helper.StringHelper.*;

/**
 * EditProfileActivity is used to allow a User to edit their profile information.
 * @author caboteja
 */

public class EditProfileActivity extends IrisActivity {

    private User loggedInUser;
    private EditProfileController controller;

    private EditText newEmail;
    private EditText newPhone;

    private Button saveChanges;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        loggedInUser = IrisProjectApplication.getCurrentUser();
        controller = (EditProfileController) createController(getIntent());

        newEmail = findViewById(R.id.edit_profile_email_edit_text);
        newPhone = findViewById(R.id.edit_profile_phone_edit_text);
        saveChanges = findViewById(R.id.edit_profile_save_changes_button);
        cancel = findViewById(R.id.edit_profile_cancel_button);

        newEmail.setText(loggedInUser.getEmail());
        newPhone.setText(loggedInUser.getPhone());

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmailText = newEmail.getText().toString();
                String newPhoneText = newPhone.getText().toString();

                String[] fields = {newEmailText, newPhoneText};
                if (hasEmptyString(Arrays.asList(fields))) {
                    Toast.makeText(EditProfileActivity.this, getString(R.string.edit_profile_failure), Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_CANCELED);
                    return;
                } else {
                    loggedInUser.updateProfile(newEmailText, newPhoneText);

                    // Updated user is now sent into controller to update database with corresponding information
                    controller.updateProfile(loggedInUser);

                    Toast.makeText(EditProfileActivity.this, getString(R.string.edit_profile_success), Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected IrisController createController(Intent intent) {
        return new EditProfileController(intent);
    }
}
