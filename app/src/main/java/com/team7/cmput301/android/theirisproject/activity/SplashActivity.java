/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.controller.LoginController;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.SleepTask;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;
import pl.droidsonroids.gif.GifImageView;


/**
 * Used to display a splash screen to the user. If the app user already has used an account for this
 * app, it will automatically log-in to that account and redirect them to either PatientList or
 * ProblemList activity.
 *
 * Otherwise, it redirects them to the login page where they can either transfer an account or register.
 *
 * @author jtfwong
 * @author Jmmxp
 * @author anticobalt
 */
public class SplashActivity extends IrisActivity<Void> {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static int taskCount = 0;
    private static Class startActivity;

    private LoginController controller;
    GifDrawable drawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_animate_background);

        GifImageView image = findViewById(R.id.gif_image);
        drawable = (GifDrawable) image.getDrawable();

        controller = (LoginController) createController(getIntent());

        // Execute task for the sole purpose of stalling so that the
        // logo animation can play, since data is loaded too fast
        Callback<Void> cb = new Callback<Void>() {
            @Override
            public void onComplete(Void res) {
                waitForTasks(null);
            }
        };
        new SleepTask(cb).execute();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPref.getString(getString(R.string.shared_pref_username_key), "");

        // Redirect to login/register activity because this user isn't valid
        if (username == null || username.isEmpty()) {
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
                        Toast.makeText(SplashActivity.this, getString(R.string.login_failure_internet_or_not_found), Toast.LENGTH_SHORT).show();
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
                waitForTasks(activity);
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
     * Landing site for the two callbacks associated with Tasks that return to activity.
     * Starts user activity when both Tasks have finished.
     *
     * @param activity Denotes User's starting activity, if applicable; null otherwise
     */
    private synchronized void waitForTasks(Class<?> activity) {
        taskCount++;
        if (activity != null) {
            startActivity = activity;
        }
        if (taskCount == 2) {
            finish();
            startUserActivity(startActivity);
        }
    }

    /**
     * startUserActivity takes in the targetActivity to goto if
     * login is successful.
     *
     * @param targetActivity: an activity class for the intent
     * */
    private void startUserActivity(Class<?> targetActivity) {
        Intent intent = new Intent(SplashActivity.this, targetActivity);
        intent.putExtra(Extras.EXTRA_USER_ID, IrisProjectApplication.getCurrentUser().getId());
        startActivity(intent);
    }

    @Override
    protected IrisController createController(Intent intent) {
        return new LoginController(intent);
    }
}