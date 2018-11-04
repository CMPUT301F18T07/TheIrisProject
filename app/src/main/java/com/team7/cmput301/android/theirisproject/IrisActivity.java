/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.team7.cmput301.android.theirisproject.controller.IrisController;

/**
 * An type of Activity that uses a IrisController to interact with and update with a Model object,
 *
 * @author anticobalt
 * @see IrisController
 */
public abstract class IrisActivity extends AppCompatActivity {

    /**
     * Initialize IrisController with the Model object.
     * @param intent The Intent given by the IrisActivity that started this IrisActivity
     * @return The IrisController (e.g. ProblemController)
     */
    protected abstract IrisController createController(Intent intent);

}
