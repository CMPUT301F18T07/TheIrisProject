/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;

/**
 * A type of Activity that uses a IrisController to interact with and update with a Model object,
 *
 * @author anticobalt
 * @see IrisController
 */
public abstract class IrisActivity<M> extends AppCompatActivity {

    /**
     * Initialize IrisController with an Intent (same Intent used to get this IrisActivity
     * up and running).
     * @param intent The Intent given by the IrisActivity that started this IrisActivity
     * @return The IrisController (e.g. ProblemController)
     */
    protected abstract IrisController createController(Intent intent);

    protected void showOfflineFatalToast(Context context) {
        Toast.makeText(context, R.string.offline_fatal_error, Toast.LENGTH_SHORT).show();
    }

    protected void showOfflineFetchToast(Context context) {
        Toast.makeText(context, R.string.offline_fetch_error, Toast.LENGTH_SHORT).show();
    }

    protected void showOfflineUploadToast(Context context) {
        Toast.makeText(context, R.string.offline_upload_error, Toast.LENGTH_SHORT).show();
    }
  
}
