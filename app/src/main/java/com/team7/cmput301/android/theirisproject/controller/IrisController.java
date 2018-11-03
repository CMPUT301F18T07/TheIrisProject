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

    /**
     * The constructor of an IrisController subclass can take in a specific Model class
     * (e.g. Problem) and use it make a call to super(), as long as that Model implements Parcelable.
     * @param model Any Parcelable object
     */
    IrisController(Parcelable model){
        this.model = model;
    }

    /**
     * Put the associated model object into the passed Intent, and return it.
     * @param intent Calling Activity's Intent
     * @return Modified Intent
     */
    public Intent bindModelToIntent(Intent intent){
        intent.putExtra("model", model);
        return intent;
    }

}
