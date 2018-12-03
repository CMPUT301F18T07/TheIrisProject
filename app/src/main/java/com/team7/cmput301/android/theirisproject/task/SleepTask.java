/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.helper.Timer;

public class SleepTask extends AsyncTask<Void, Void, Void> {

    Callback<Void> cb;

    public SleepTask(Callback<Void> cb) {
        this.cb = cb;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Timer.sleep(4000);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        cb.onComplete(aVoid);

    }
}
