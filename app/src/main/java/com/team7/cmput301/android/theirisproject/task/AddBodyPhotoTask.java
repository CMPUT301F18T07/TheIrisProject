/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;

import java.util.ArrayList;

public class AddBodyPhotoTask extends AsyncTask<ArrayList<Bitmap>, Void, Boolean> {
    private Callback cb;
    public AddBodyPhotoTask(Callback cb) {
        this.cb = cb;
    }
    @Override
    protected Boolean doInBackground(ArrayList<Bitmap>... params) {
        for(Bitmap img: params[0]) {
            IrisProjectApplication.getDB().execute()
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        cb.onComplete(success);
    }
}
