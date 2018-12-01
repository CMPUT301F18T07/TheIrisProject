/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.controller.LoginController;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.prefs.Preferences;


/**
 * Used to display a splash screen to the user. If the app user already has used an account for this
 * app, it will automatically log-in to that account and redirect them to either PatientList or
 * ProblemList activity.
 *
 * Otherwise, it redirects them to the login page where they can either transfer an account or register.
 *
 * @author jtfwong
 * @author Jmmxp
 */
public class SplashActivity extends IrisActivity<Void> {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private LoginController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IrisProjectApplication.setApplicationContext(getApplicationContext());
        controller = (LoginController) createController(getIntent());
        IrisProjectApplication.initBulkUpdater();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPref.getString(getString(R.string.shared_pref_username_key), "");

        // Redirect to login/register activity because this user isn't valid
        if (username == null || username.isEmpty()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            // Otherwise if username exists in DB, login with that user and wait until we load their data
            controller.loginUser(username, new Callback<Boolean>() {
                @Override
                public void onComplete(Boolean success) {
                    // Start ProblemList or PatientList activity if login is successful,
                    // otherwise redirect to login/register activity
                    if (success) {
                        Toast.makeText(SplashActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                        buildUserSession();
                        // Build user session will start the correct User activity
                    } else {
                        finish();
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                }
            });
        }

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
                // Put finish here so that the splash screen stays until we're done loading
                finish();
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
        Intent intent = new Intent(SplashActivity.this, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);  // prevents multiple occurrences
        intent.putExtra("user", IrisProjectApplication.getCurrentUser().getId());
        startActivity(intent);
    }

    @Override
    protected IrisController createController(Intent intent) {
        return new LoginController(intent);
    }
}
