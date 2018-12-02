/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import java.io.IOException;

import io.searchbox.core.Delete;

/**
 * Deletes a single Record Photo from elasticsearch
 *
 * @author itstc
 * @author anticobalt
 */
public class DeleteRecordPhotoTask extends AsyncTask<RecordPhoto, Void, Boolean> {

    private Callback<Boolean> callback;

    public DeleteRecordPhotoTask(Callback<Boolean> callback) {
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(RecordPhoto... params) {

        try {
            RecordPhoto target = params[0];
            Delete delete = new Delete.Builder(target.getId()).index(IrisProjectApplication.INDEX).type("recordphoto").build();
            return IrisProjectApplication.getDB().execute(delete).isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    protected void onPostExecute(Boolean res) {
        super.onPostExecute(res);
        callback.onComplete(res);

    }
}
