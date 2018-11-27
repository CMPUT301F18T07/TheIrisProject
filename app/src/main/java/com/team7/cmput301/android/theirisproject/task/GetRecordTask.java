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
        // get request to database for the record with 'id'
        Get get = new Get.Builder(IrisProjectApplication.INDEX, params[0]).type("record").build();
        try {
            return IrisProjectApplication.getDB().execute(get).getSourceAsObject(Record.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Record();
    }

    @Override
    protected void onPostExecute(Record record) {
        super.onPostExecute(record);
        cb.onComplete(record);
    }
}
