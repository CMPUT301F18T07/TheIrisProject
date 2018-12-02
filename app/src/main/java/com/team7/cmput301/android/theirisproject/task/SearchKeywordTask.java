/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;

/**
 * SearchKeywordTask searches problems and records based on the keywords given
 *
 * @author itstc
 */
public class SearchKeywordTask<M> extends AsyncTask<String, Void, List<M>> {

    private String querySearch = "{\n" +
            "  \"query\": {\n" +
            "    \"bool\" : {\n" +
            "    \"must\": {\n" +
            "    \t\"term\": {\"user\":\"%s\"}\n" +
            "    \t},\n" +
            "    \"should\" : [\n" +
            "        { \"match\" : { \"title\" : \"%s\" } },\n" +
            "        { \"match\" : { \"desc\" : \"%s\" } }\n" +
            "      ],\n" +
            "    \"boost\" : 1.0,\n" +
            "    \"minimum_should_match\" : 1\n" +
            "    }\n" +
            "  }\n" +
            "}";

    private Callback cb;
    private String type;
    private Class targetClass;
    public SearchKeywordTask(Callback cb, String type, Class targetClass) {
        this.cb = cb;
        this.type = type;
        this.targetClass = targetClass;
    }

    @Override
    protected List<M> doInBackground(String... strings) {
        Search search = new Search.Builder(String.format(querySearch, strings[0], strings[1], strings[1]))
                .addIndex(IrisProjectApplication.INDEX).addType(type).build();
        try {
            return IrisProjectApplication.getDB().execute(search).getSourceAsObjectList(targetClass, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<M> ms) {
        super.onPostExecute(ms);
        cb.onComplete(ms);
    }
}
