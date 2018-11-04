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
 * is responsible for updating and interpreting the Model object, as well as saving it to databases.
 *
 * @author anticobalt
 * @see IrisActivity
 */
public abstract class IrisController {

    protected Object model;

    abstract void makeModelFromDB();
    abstract void saveModelToLocalDB();
    abstract void saveModelToOnlineDB();

}
