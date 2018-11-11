/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.tasks;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.RecordList;

import java.io.IOException;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Asynchronously searches ElasticSearch DB to ultimately make a RecordList
 * @author anticobalt
 * @see com.team7.cmput301.android.theirisproject.controller.RecordListController
 */
public class GetRecordListTask extends AsyncTask<String, Void, SearchResult> {

    private SearchResult res = null;
    private Callback<RecordList> callback;

    public GetRecordListTask(Callback<RecordList> callback) {
        this.callback = callback;
    }

    /**
     * Get Records that belong to Problem from ElasticSearch DB
     * @param strings [0] is problem's JestID
     * @return
     */
    @Override
    protected SearchResult doInBackground(String... strings) {

        String query = "{\"query\": {\"term\": {\"user\": \"" + strings[0] + "\"}}}";
        String type = "record";

        try {
            Search search = new Search.Builder(query)
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType(type)
                    .build();
            res = IrisProjectApplication.getDB().execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;

    }
}
