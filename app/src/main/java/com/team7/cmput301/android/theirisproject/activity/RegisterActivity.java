/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.controller.RegisterController;
import com.team7.cmput301.android.theirisproject.helper.StringHelper;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.User.UserType;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.Arrays;

/**
 * Activity that is used to help registerUser new users into the database
 * Uses its designated controller, RegisterController to handle the updating of the database
 *
 * @author Jmmxp
 * @see RegisterController
 */

public class RegisterActivity extends IrisActivity {

    private RegisterController controller;

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private RadioGroup userRadioGroup;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        controller = (RegisterController) createController(getIntent());

        usernameEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        userRadioGroup = findViewById(R.id.user_radio_group);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    /***
     * Get form data, then send to controller to register user
     */
    private void registerUser() {
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phoneNumber = phoneEditText.getText().toString();

        String[] fields = {username, email, phoneNumber};

        if (StringHelper.hasEmptyString(Arrays.asList(fields))) {
            Toast.makeText(this, getString(R.string.register_incomplete), Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length() < 8) {
            Toast.makeText(this, getString(R.string.register_short_username), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.contains("@")) {
            Toast.makeText(this, getString(R.string.register_invalid_email), Toast.LENGTH_SHORT).show();
            return;
        }
        if (phoneNumber.length() < 10) {
            Toast.makeText(this, getString(R.string.register_invalid_phone), Toast.LENGTH_SHORT).show();
            return;
        }


        int radioButtonId = userRadioGroup.getCheckedRadioButtonId();
        UserType type;
        switch (radioButtonId) {
            case R.id.patient_radio_button:
                type = UserType.PATIENT;
                break;
            case R.id.care_provider_radio_button:
                type = UserType.CARE_PROVIDER;
                break;
            default:
                type = UserType.PATIENT;
                break;
        }

        controller.createUser(username, email, phoneNumber, type, new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean registerSuccess) {
                if (registerSuccess) {
                    Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                    IrisProjectApplication.loginCurrentUser(username);

                    finish();
                    startActivity(new Intent(RegisterActivity.this, SplashActivity.class));

                } else {
                    Toast.makeText(RegisterActivity.this, R.string.register_failure, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected IrisController createController(Intent intent) {
        return new RegisterController(intent);
    }

}
