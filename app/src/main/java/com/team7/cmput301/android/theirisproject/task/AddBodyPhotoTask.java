/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.core.Index;

/**
 * AddBodyPhotoTask asynchronously adds a bodyphoto to the database
 *
 * @author itstc
 * */
public class AddBodyPhotoTask extends AsyncTask<BodyPhoto, Void, BodyPhoto> {
    private Callback cb;
    public AddBodyPhotoTask(Callback cb) {
        this.cb = cb;
    }
    @Override
    protected BodyPhoto doInBackground(BodyPhoto... params) {
        try {
            Index post = new Index.Builder(params[0]).index(IrisProjectApplication.INDEX).type("bodyphoto").build();
            String res = IrisProjectApplication.getDB().execute(post).getId();
            params[0].setId(res);
            return params[0];
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(BodyPhoto res) {
        super.onPostExecute(res);
        cb.onComplete(res);
    }
}
