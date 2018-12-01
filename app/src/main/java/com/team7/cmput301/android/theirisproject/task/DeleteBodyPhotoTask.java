/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

import java.io.IOException;

import io.searchbox.core.Delete;

public class DeleteBodyPhotoTask extends AsyncTask<BodyPhoto, Void, Boolean> {

    private Callback callback;
    public DeleteBodyPhotoTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(BodyPhoto... params) {
        try {
            BodyPhoto target = params[0];
            Delete delete = new Delete.Builder(target.getId()).index(IrisProjectApplication.INDEX).type("bodyphoto").build();
            IrisProjectApplication.getAppContext().deleteFile(String.format(BodyPhoto.FILE_FORMAT, target.getMetaData(), target.getDate()));
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
