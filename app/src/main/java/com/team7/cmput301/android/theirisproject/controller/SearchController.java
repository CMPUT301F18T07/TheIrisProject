/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.SearchState;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.ArrayList;
import java.util.List;

public class SearchController {
    private String userId;
    private List<Problem> problems;
    private List<Record> records;

    private SearchState searchState = new SearchState.SearchKeyword();

    public SearchController(Intent intent) {

        // set the user id to given id from intent or the current user
        String id = intent.getStringExtra(Extras.EXTRA_USER_ID);
        if (id != null) this.userId = id;
        else this.userId = IrisProjectApplication.getCurrentUser().getId();

        // populate problems/records with empty arraylist
        problems = new ArrayList<>();
        records = new ArrayList<>();
    }

    public List<Problem> getProblems() { return problems; }
    public List<Record> getRecords() { return records; }

    public void setSearchOption(SearchState state) {
        searchState = state;
    }

    public void querySearch(String keyword, Callback cb) {
        searchState.querySearchRecords(userId, keyword, new Callback<List<Record>>() {
            @Override
            public void onComplete(List<Record> res) {
                records.addAll(res);
                cb.onComplete(res);
            }
        });
    }
}
