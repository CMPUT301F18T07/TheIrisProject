/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.controller.LoginController;
import com.team7.cmput301.android.theirisproject.model.Profile;

public class LoginActivity extends IrisActivity {

    private Button loginButton;
    private Button registerButton;

    private IrisController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        // First activity must create fake Intent
        Intent intent = new Intent();
        intent.putExtra("model", new Profile(null, null, null));
        this.controller = createController(intent);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent = fillExtras(intent, LoginActivity.this.controller);
                startActivity(intent);
            }
        });

    }

    @Override
    protected IrisController createController(Intent intent) {
        return new LoginController(intent);
    }
}
