/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import java.io.IOException;

import io.searchbox.core.Index;

/**
 * Asynchronously adds a single Record Photo to elasticsearch DB.
 *
 * @author anticobalt
 * @see EditRecordTask
 * */
public class AddRecordPhotoTask extends AsyncTask<RecordPhoto, Void, Boolean> {

    Callback<Boolean> cb;

    public AddRecordPhotoTask(Callback<Boolean> cb) {
        this.cb = cb;
    }

    @Override
    protected Boolean doInBackground(RecordPhoto... params) {
        Boolean success;
        try {
            Index post = new Index.Builder(params[0]).index(IrisProjectApplication.INDEX).type("recordphoto").build();
             success = IrisProjectApplication.getDB().execute(post).isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        cb.onComplete(aBoolean);
    }
}
