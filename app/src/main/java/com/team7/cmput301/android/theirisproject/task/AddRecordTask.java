/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Record;

import java.io.IOException;

import io.searchbox.core.Index;

/**
 * AddRecordTask is an async task that will dispatch
 * when the user submits a new record and the task
 * will add it to the database returning its id
 *
 * @author itstc
 * */
public class AddRecordTask extends AsyncTask<Record, Void, String> {
    Callback cb;
    public AddRecordTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected String doInBackground(Record... params) {
        Index add = new Index.Builder(params[0])
                .index(IrisProjectApplication.INDEX)
                .type("record")
                .build();
        try {
            return IrisProjectApplication.getDB().execute(add).getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        cb.onComplete(res);
    }
}
