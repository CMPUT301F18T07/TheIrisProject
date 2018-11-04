/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.model;

import com.team7.cmput301.android.theirisproject.IrisActivity;

import java.util.ArrayList;

/**
 * IrisModel is an abstraction for our models to update attached views
 * The Flow of updating IrisActivities is as follows
 * > controller runs asyncTask to populate model
 * > once populated, controller invokes updateViews for model
 * > updateViews calls IrisActivity.update() for all IrisActivity in 'views'
 * > connected IrisActivities are updated accordingly with new populated data
 * */
public abstract class IrisModel<V extends IrisActivity> {
    private ArrayList<V> views;

    /**
     * addView is a function that connects an IrisActivity with IrisModel
     * @params V view: target IrisActivity to connect
     * @return void
     * */
    public void addView(V view) {
        if(!views.contains(view)) views.add(view);
    }

    /**
     * removeView is a function that disconnects an IrisActivity with IrisModel
     * @params V view: target IrisActivity to disconnect
     * @return void
     * */
    public void removeView(V view) {
        views.remove(view);
    }

    /**
     * updateViews is a function that updates all connected IrisActivity
     * in 'views' with new populated data within IrisModel
     * @params void
     * @return void
     * */
    public void updateViews() {
        for(V view: views) view.update(this);
    }
}
