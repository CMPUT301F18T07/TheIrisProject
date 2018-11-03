/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Parcelable;

import com.team7.cmput301.android.theirisproject.IrisActivity;

/**
 * A type of controller that is used by an IrisActivity to manage a Model object. The IrisController
 * is responsible for updating the Model object, interpreting the Model object, and binding it to
 * an Intent so that the IrisActivity can start another IrisActivity.
 *
 * @author anticobalt
 * @see IrisActivity
 */
public abstract class IrisController {

    protected Parcelable model;

    IrisController(Parcelable model){
        this.model = model;
    }

    public Intent bindModelToIntent(Intent intent){
        intent.putExtra("model", model);
        return intent;
    }

}
