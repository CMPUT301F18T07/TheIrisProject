/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.core.Index;

/**
 * AddBodyPhotoTask asynchronously adds bodyphotos to the database
 *
 * @author itstc
 * */
public class AddBodyPhotoTask extends AsyncTask<ArrayList<BodyPhoto>, Void, Boolean> {
    private Callback cb;
    public AddBodyPhotoTask(Callback cb) {
        this.cb = cb;
    }
    @Override
    protected Boolean doInBackground(ArrayList<BodyPhoto>... params) {
        Boolean res = true;
        try {
            for (BodyPhoto bp: params[0]) {
                Index post = new Index.Builder(bp).index(IrisProjectApplication.INDEX).type("bodyphoto").build();
                res = IrisProjectApplication.getDB().execute(post).isSucceeded();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        cb.onComplete(success);
    }
}
