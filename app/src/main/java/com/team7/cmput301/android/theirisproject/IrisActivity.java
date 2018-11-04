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
 * An type of Activity that uses a IrisController to a) interact with and update with a Model object,
 * and b) get Intent extra data in order start another IrisActivity.
 *
 * @author anticobalt
 * @see IrisController
 */
public abstract class IrisActivity<M> extends AppCompatActivity {

    /**
     * Initialize IrisController with the Model object.
     * Typically involves calling constructor with Intent's Parcelable extra (as the Intent holds
     * the Model object); if this is done, the extra will have to be cast to whatever type the
     * controller's constructor accepts (e.g. Problem).
     * @param intent The Intent given by the IrisActivity that started this IrisActivity
     * @return The IrisController (e.g. ProblemController)
     */
    protected abstract IrisController createController(Intent intent);

    /**
     * Use IrisController to fill Intent with Model object data
     * @param intent Intent that describes the transition from this IrisActivity to another
     * @param controller IrisController associated with this IrisActivity
     * @return The original passed Intent, now modified with proper extras
     */
    protected Intent fillExtras(Intent intent, IrisController controller){
        return controller.bindModelToIntent(intent);
    }

    public abstract void update(M model);

}
