/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.content.Context;
import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.LocalStorageHandler;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

/**
 * CacheBodyPhotoTask will run in the background to save body photos
 * in the apps internal storage for quicker loading of images on
 * BodyPhotoListActivity
 *
 * @see com.team7.cmput301.android.theirisproject.activity.BodyPhotoListActivity
 * @author itstc
 * */
public class CacheBodyPhotoTask extends AsyncTask<BodyPhoto, Void, Void> {
    private Context context = IrisProjectApplication.getAppContext();
    @Override
    protected Void doInBackground(BodyPhoto... params) {
        // make a new file or override the existing file
        BodyPhoto bp = params[0];
        LocalStorageHandler.writeBodyPhotoFile(IrisProjectApplication.getAppContext(), bp);
        return null;
    }
}
