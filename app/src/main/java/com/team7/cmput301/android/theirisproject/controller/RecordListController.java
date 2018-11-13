/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.activity.RecordListActivity;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.GetRecordListTask;

import io.searchbox.core.SearchResult;

/**
 * Controller for RecordListActivity
 *
 * @author anticobalt
 * @see RecordListActivity
 * @see GetRecordListTask
 */
public class RecordListController extends IrisController<RecordList> {

    private String problemId;
    private RecordList records;
    private Callback<SearchResult> taskCallback;

    public RecordListController(Intent intent){
        super(intent);
        // TODO: remove test case
        problemId = "AWcA2xC7mS3Zy2E5YM_s"; //= intent.getStringExtra("problemId");
        records = model; // aliasing for clarity
    }

    public void getRecords(Callback<RecordList> contCallback){

        // Make the task callback
        taskCallback = new Callback<SearchResult>(){
            /* When complete, convert the search results into RecordList,
             * save, then prompt update of views
             */
            @Override
            public void onComplete(SearchResult res) {
                RecordList results = new RecordList(res.getSourceAsObjectList(Record.class, true));
                RecordListController.this.records = results;
                contCallback.onComplete(results);
            }
        };

        // execute task to get Records from, using task callback
        new GetRecordListTask(taskCallback).execute(problemId);

    }

    @Override
    RecordList getModel(Bundle data) {
        return new RecordList();
    }
}
