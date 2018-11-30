/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
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
        records = model; // aliasing for clarity
    }

    /**
     * Do callback by fill calling IrisActivity with Records.
     * May do callback straightaway, or require an additional async task, depending on current user type.
     *
     * @param contCallback Callback with IrisActivity's specified actions
     * @return True if no issues, False if internet-related issues
     */
    public Boolean fillRecords(Callback<RecordList> contCallback){

        // Assume can't get latest Record data from online,
        // and unconditionally fill activity with local version of Records
        Boolean fullSuccess = false;
        contCallback.onComplete(records);

        switch (IrisProjectApplication.getCurrentUser().getType()) {

            case PATIENT:
                fullSuccess = true;
                break;

            case CARE_PROVIDER:
                if (IrisProjectApplication.isConnectedToInternet()) {
                    fetchRecordsFromOnline(contCallback);
                    fullSuccess = true;
                }
                break;

            default:
                break;

        }

        return fullSuccess;

    }

    private void fetchRecordsFromOnline(Callback contCallback) {
        // Make the task callback
        taskCallback = new Callback<SearchResult>() {
            /* When complete, convert the search results into RecordList,
             * save, then prompt update of views
             */
            @Override
            public void onComplete(SearchResult res) {
                RecordList results = new RecordList(res.getSourceAsObjectList(Record.class, true));
                records.asList().clear();
                records.asList().addAll(results.asList());
                contCallback.onComplete(results);
            }
        };

        // execute task to get Records from, using task callback
        new GetRecordListTask(taskCallback).execute(problemId);
    }

    @Override
    RecordList getModel(Bundle data) {
        problemId = data.getString(Extras.EXTRA_PROBLEM_ID);
        return IrisProjectApplication.getProblemById(problemId).getRecords();
    }

}
