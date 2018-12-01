/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;

/**
 * GetRecordPhotoTask queries the record photos associated to the record
 *
 * @author itstc
 * */
public class GetRecordPhotoTask extends AsyncTask<String, Void, List<RecordPhoto>> {
    private String searchQuery = "{\"query\":{\"term\":{\"recordId\": \"%s\"}}}";
    Callback cb;
    public GetRecordPhotoTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected List<RecordPhoto> doInBackground(String... params) {
        // query to retrieve record photos with 'id' as reference id
        Search search = new Search.Builder(String.format(searchQuery, params[0]))
                .addIndex(IrisProjectApplication.INDEX)
                .addType("recordphoto")
                .build();
        List<RecordPhoto> photosResult = new ArrayList<>();
        try {
            photosResult = IrisProjectApplication
                    .getDB()
                    .execute(search)
                    .getSourceAsObjectList(RecordPhoto.class, true);
            for(RecordPhoto rp: photosResult) rp.convertBlobToBitmap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photosResult;
    }

    @Override
    protected void onPostExecute(List<RecordPhoto> recordPhotos) {
        super.onPostExecute(recordPhotos);
        cb.onComplete(recordPhotos);
    }
}