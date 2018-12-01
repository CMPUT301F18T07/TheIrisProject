/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.ImageConverter;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * CacheBodyPhotoTask will run in the background to save body photos
 * in the apps internal storage for quicker loading of images on
 * BodyPhotoListActivity
 *
 * @see com.team7.cmput301.android.theirisproject.activity.BodyPhotoListActivity
 * @author itstc
 * */
public class CacheBodyPhotoTask extends AsyncTask<BodyPhoto, Void, Void> {

    private Context context;
    public CacheBodyPhotoTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(BodyPhoto... params) {
        // make a new file or override the existing file
        BodyPhoto bp = params[0];
        FileOutputStream fos;
        String filename = String.format(BodyPhoto.FILE_FORMAT, bp.getMetaData(), bp.getDate());
        new File(context.getFilesDir(), filename);

        // write the blob to the file
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(ImageConverter.convertBitmapToBytes(bp.getPhoto()));
        } catch (IOException e) {
            e.printStackTrace();
            // if an error occurs, delete the file
            context.deleteFile(filename); }
        return null;
    }
}
