/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.activity.IrisActivity;

/**
 * A type of controller that is used by an IrisActivity to manage a Model object. The IrisController
 * is responsible for updating and interpreting the Model object.
 * It connects the model and IrisActivity.
 *
 * @author anticobalt
 * @see IrisActivity
 */
public abstract class IrisController<M> {

    protected M model;

    public IrisController(Intent intent){
        // Isolate extras, then get model using extras
        Bundle data = intent.getExtras();
        this.model = getModel(data);
    }

    /**
     * Determines if currently connected to internet.
     * https://stackoverflow.com/a/32771164
     * @param context
     * @return True if connected, false if not
     */
    public Boolean isConnectedToInternet(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        } else {
            // can't determine if connected to internet, so assume not
            return false;
        }
        return (activeNetwork != null);

    }

    // returns a model instance (obtained from singleton or constructed from DB),
    // using data from a Bundle
    abstract M getModel(Bundle data);

}
