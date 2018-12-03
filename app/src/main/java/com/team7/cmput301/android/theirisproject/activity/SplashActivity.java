/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
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
    GifImageView imageView;
    GifDrawable drawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_animate_background);
        imageView = findViewById(R.id.gif_image);
        drawable = (GifDrawable) imageView.getDrawable();

        controller = (LoginController) createController(getIntent());
        setUpSplash();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPref.getString(getString(R.string.shared_pref_username_key), "");

        // Redirect to login/register activity because this user isn't valid
        if (username == null || username.isEmpty()) {
            waitForTasks(LoginActivity.class);
        } else {
            // Otherwise if username exists in DB, login with that user and wait until we load their data
            controller.loginUser(username, new Callback<Boolean>() {
                @Override
                public void onComplete(Boolean success) {
                    // Start ProblemList or PatientList activity if login is successful,
                    // otherwise redirect to login/register activity
                    if (success) {
                        buildUserSession();
                        // Build user session will start the correct User activity
                    } else {
                        Toast.makeText(SplashActivity.this,
                                getString(R.string.login_failure_internet_or_not_found),
                                Toast.LENGTH_SHORT).show();
                        waitForTasks(LoginActivity.class);
                    }
                }
            });
        }

    }

    private void setUpSplash() {

        // Execute task for the sole purpose of stalling so that the
        // logo animation can play, since data is loaded too fast
        Callback<Void> cb = new Callback<Void>() {
            @Override
            public void onComplete(Void res) {
                waitForTasks(null);
            }
        };
        new SleepTask(cb).execute();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitForTasks(null);
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
     * Landing site all flow of executions in this activity.
     * Starts user activity when two tasks (getting user data, and playing splash screen animation)
     * have completed.
     *
     * @param activity Denotes activity to be redirected to, if applicable; null otherwise
     */
    private synchronized void waitForTasks(Class<?> activity) {
        taskCount++;
        if (activity != null && startActivity == null) {
            // only allow startActivity to be set once
            startActivity = activity;
        }
        if (startActivity == null) System.out.println(String.valueOf(taskCount));
        else System.out.println(String.valueOf(taskCount) + " " + startActivity.getSimpleName());
        if (taskCount >= 2 && startActivity != null) { // greater than two possible if double click on logo occurs
            finish();
            if (startActivity == LoginActivity.class) {
                startActivity(new Intent(this, startActivity));
            } else {
                startUserActivity(startActivity);
            }
            startActivity = null;
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
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);  // prevents multiple occurrences
        startActivity(intent);
    }

    @Override
    protected IrisController createController(Intent intent) {
        return new LoginController(intent);
    }
}