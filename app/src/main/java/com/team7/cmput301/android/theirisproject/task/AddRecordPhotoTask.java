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
public class AddRecordPhotoTask extends AsyncTask<RecordPhoto, Void, Void> {

    @Override
    protected Void doInBackground(RecordPhoto... params) {
        try {
            Index post = new Index.Builder(params[0]).index(IrisProjectApplication.INDEX).type("recordphoto").build();
            IrisProjectApplication.getDB().execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
