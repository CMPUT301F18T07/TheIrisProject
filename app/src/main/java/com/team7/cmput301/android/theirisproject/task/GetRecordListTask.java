/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.controller.RecordListController;

import java.io.IOException;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.sort.Sort;
import io.searchbox.params.Parameters;

/**
 * Asynchronously searches ElasticSearch DB to ultimately make a RecordList
 *
 * @author anticobalt
 * @see RecordListController
 */
public class GetRecordListTask extends AsyncTask<String, Void, SearchResult> {

    private SearchResult res = null;
    private Callback<SearchResult> callback;
    private Sort sort = new Sort("date", Sort.Sorting.DESC);

    public GetRecordListTask(Callback<SearchResult> callback) {
        this.callback = callback;
    }

    public GetRecordListTask(Callback<SearchResult> callback, Sort sort) {
        this(callback);
        this.sort = sort;
    }

    /**
     * Get Records that belong to Problem from ElasticSearch DB
     * @param strings [0] is problem's JestID
     * @return the results of the search, to be parsed
     */
    @Override
    protected SearchResult doInBackground(String... strings) {

        String query = "{\"query\": {\"term\": {\"problemId\": \"" + strings[0] + "\"}}}";
        String type = "record";

        try {
            Search search = new Search.Builder(query)
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType(type)
                    .addSort(sort)
                    .setParameter(Parameters.SIZE, IrisProjectApplication.SIZE)
                    .build();
            res = IrisProjectApplication.getDB().execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;

    }

    /**
     * When doInBackground() is complete, do Callback
     * @param searchResult the raw results of doInBackground() search
     */
    @Override
    protected void onPostExecute(SearchResult searchResult) {
        super.onPostExecute(searchResult);
        this.callback.onComplete(searchResult);
    }
}
