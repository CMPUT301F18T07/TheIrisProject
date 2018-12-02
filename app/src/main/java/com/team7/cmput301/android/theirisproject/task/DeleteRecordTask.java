/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;

import java.io.IOException;

import io.searchbox.core.Delete;

public class DeleteRecordTask extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        try {
            Delete delete = new Delete.Builder(params[0]).index(IrisProjectApplication.INDEX).type("record").build();
            IrisProjectApplication.getDB().execute(delete).getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
