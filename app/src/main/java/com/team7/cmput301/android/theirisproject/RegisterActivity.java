/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.controller.RegisterController;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.Profile;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.model.User.UserType;

/**
 * Activity that is used to help register new users into the database
 * Uses its designated controller, RegisterController to handle the updating of the database
 *
 * @author Jmmxp
 * @see RegisterController
 */

public class RegisterActivity extends IrisActivity {

    private IrisController controller;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private RadioGroup userRadioGroup;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.controller = createController(getIntent());

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        userRadioGroup = findViewById(R.id.user_radio_group);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = createUserFromViews();

                
            }
        });

    }

    private User createUserFromViews() {
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phoneNumber = phoneEditText.getText().toString();

        int radioButtonId = userRadioGroup.getCheckedRadioButtonId();
        UserType role;
        switch (radioButtonId) {
            case R.id.patient_radio_button:
                role = UserType.PATIENT;
                break;
            case R.id.care_provider_radio_button:
                role = UserType.CARE_PROVIDER;
                break;
            default:
                role = UserType.PATIENT;
                break;
        }

        User user;
        if (role == UserType.PATIENT) {
            user = new Patient(username, email, phoneNumber);
        } else {
            user = new CareProvider(username, email, phoneNumber);
        }

        return user;
    }

    @Override
    protected IrisController createController(Intent intent) {
        return new RegisterController(intent);
    }

    @Override
    public void render() {

    }

}
