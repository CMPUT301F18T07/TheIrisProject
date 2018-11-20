/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

/**
 * Callback is an interface that allows AsyncTasks to call functions from the
 * main thread after the async task is finished.
 *
 * @author itstc
 * */
public interface Callback<M> {

    /**
     * onComplete is the last thing an AsyncTask calls before exiting
     * so that functions from the main thread can be called right after
     * AsyncTask lifecycle ends
     *
     * @param res the new state to be passed down
     * */
    void onComplete(M res);
}
