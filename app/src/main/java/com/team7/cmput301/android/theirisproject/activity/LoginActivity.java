/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.LoginController;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.DeleteTransferCodeTask;

/**
 * LoginActivity is our landing page for the user to authenticate
 * before they are able to access the applications core features
 *
 * @author itstc
 * */
public class LoginActivity extends IrisActivity {

    private LoginController controller;
    private TextView username;
    private Button loginButton;
    private TextView registerTextView;

    private EditText transferField;
    private Button transferButton;

    private Callback loginCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controller = createController(getIntent());

        loginCallback = new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean success) {
                // Start activity if login is successful, else stay on login activity
                if (success) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_LONG).show();
                    buildUserSession();
                }
                else {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_failure), Toast.LENGTH_LONG).show();
                }
            }
        };

        // initialize android views from xml
        loginButton = findViewById(R.id.login_button);
        registerTextView = findViewById(R.id.login_register_button);
        username = findViewById(R.id.login_email_field);
        transferField = findViewById(R.id.login_transfer_code_field);
        transferButton = findViewById(R.id.login_transfer_button);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                // don't need extra data
                startActivity(intent);
            }
        });

        /*
         * loginButton's clickListener will take username input field and send intent
         * to ProblemListActivity where it will search all problems under given intent value "user"
         * */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create login request, and start new activity if id is found
                IrisProjectApplication.loginCurrentUser(username.getText().toString());
                controller.loginUser(username.getText().toString(), loginCallback);
            }
        });

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteTransferCodeTask(new Callback<String>() {
                    @Override
                    public void onComplete(String username) {
                        if (username != null) {
                            IrisProjectApplication.loginCurrentUser(username);
                            controller.loginUser(username, loginCallback);
                            Toast.makeText(LoginActivity.this, R.string.login_transfer_success, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_transfer_failure, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute(transferField.getText().toString());
            }
        });

    }

    /**
     * Push the update queue backups to online, get all the logged in User's associated data,
     * then start a specified activity for them.
     */
    private void buildUserSession() {

        Callback callbackToStart = new Callback() {
            @Override
            public void onComplete(Object res) {
                Class activity = controller.getStartingActivity();
                startUserActivity(activity);
            }
        };

        Callback<Boolean> callbackToFetch = new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean res) {
                controller.fetchAllUserData(callbackToStart);
            }
        };

        IrisProjectApplication.flushUpdateQueueBackups(callbackToFetch);

    }

    /**
     * startUserActivity takes in the targetActivity to goto if
     * login is successful.
     *
     * @param targetActivity: an activity class for the intent
     * */
    private void startUserActivity(Class<?> targetActivity) {
        Intent intent = new Intent(LoginActivity.this, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);  // prevents multiple occurrences
        intent.putExtra(Extras.EXTRA_USER_ID, IrisProjectApplication.getCurrentUser().getId());
        startActivity(intent);
    }

    @Override
    protected LoginController createController(Intent intent) {
        return new LoginController(intent);
    }

}
