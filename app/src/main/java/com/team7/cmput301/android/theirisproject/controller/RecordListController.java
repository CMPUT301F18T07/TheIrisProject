/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.activity.RecordListActivity;
import com.team7.cmput301.android.theirisproject.model.User;
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
        problemId = intent.getStringExtra("problemId");
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
                updateUserRecordList(results);
                RecordListController.this.records = results;
                contCallback.onComplete(results);
            }
        };

        // execute task to get Records from, using task callback
        new GetRecordListTask(taskCallback).execute(problemId);

    }

    /**
     * Find the local version of the RecordList and update it with the elasticsearch version.
     * Elasticsearch version only has IDs of Records, not actual Records, so searching is required
     *
     * @param results the elasticsearch version of the RecordList
     */
    private void updateUserRecordList(RecordList results) {

        User current_user = IrisProjectApplication.getCurrentUser();

        Problem problem = null;
        if (current_user.getType().equals(User.UserType.PATIENT)) {
             problem = ( (Patient) IrisProjectApplication.getCurrentUser() ).getProblemById(problemId);
        }
        else if (current_user.getType().equals(User.UserType.CARE_PROVIDER)) {
            problem = ( (CareProvider) IrisProjectApplication.getCurrentUser() ).getPatientProblemById(problemId);
        }

        // update the local version of RecordList with the results
        if (problem != null) {
            problem.setRecords(results);
        }
        else {
            System.err.println(String.format("%s could not handle %s user type",
                    this.getClass().getSimpleName(), current_user.getType().toString()));
        }
    }

    @Override
    RecordList getModel(Bundle data) {
        return new RecordList();
    }
}
