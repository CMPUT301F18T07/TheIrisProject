/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Get;
import io.searchbox.core.Search;

/**
 * GetRecordTask is an async task that fetches a Record model
 * from the database given a record id
 *
 * @author itstc
 * */
public class GetRecordTask extends AsyncTask<String, Void, Record> {

    private String searchQuery = "{\"query\":{\"term\":{\"recordId\": \"%s\"}}}";
    private Callback cb;
    public GetRecordTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected Record doInBackground(String... params) {
        Record recordResult = getRecordById(params[0]);
        recordResult.setRecordPhotos(getRecordPhotosById(params[0]));
        return recordResult;
    }

    private Record getRecordById(String id) {
        // get request to database for the record with 'id'
        Get get = new Get.Builder(IrisProjectApplication.INDEX, id).type("record").build();
        try {
            return IrisProjectApplication.getDB().execute(get).getSourceAsObject(Record.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Record();
    }

    private List<RecordPhoto> getRecordPhotosById(String id) {
        // query to retrieve record photos with 'id' as reference id
        Search search = new Search.Builder(String.format(searchQuery, id))
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
    protected void onPostExecute(Record record) {
        super.onPostExecute(record);
        cb.onComplete(record);
    }
}
