/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.task.LoginTask;

public class LoginActivity extends IrisActivity {
    private TextView username;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        username = findViewById(R.id.username_edit_text2);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                // don't need extra data
                startActivity(intent);
            }
        });

        /**
         * loginButton's clickListener will take username input field and send intent
         * to ProblemListActivity where it will search all problems under given intent value "user"
         * */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create login request, and start new activity if id is found
                new LoginTask(new Callback<String>() {
                    @Override
                    public void onComplete(String res) {
                        // act accordingly based on result "" if error, else success
                        if(res != "") {
                            // start new activity with given id from login request
                            Intent intent = new Intent(LoginActivity.this, ProblemListActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this, "Incorrect Login!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).execute(username.getText().toString());
            }
        });

    }

    @Override
    protected IrisController createController(Intent intent) {
        return null; // no reason for controller in this activity
    }

}
