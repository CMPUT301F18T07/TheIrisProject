/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.activity.RecordListActivity;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.GetRecordListTask;

/**
 * Controller for RecordListActivity
 * @author anticobalt
 * @see RecordListActivity
 * @see GetRecordListTask
 */
public class RecordListController extends IrisController<RecordList> {

    private String problemId;
    private RecordList records;

    public RecordListController(Intent intent){
        super(intent);
        problemId = intent.getStringExtra("problemId");
        records = model; // aliasing for clarity
    }

    public void getRecords(Callback<RecordList> cb){
        new GetRecordListTask(new Callback<RecordList>() {
            @Override
            public void onComplete(RecordList records) {
                RecordListController.this.records = records;
                cb.onComplete(records);
            }
        }).execute(problemId);
    }

    @Override
    RecordList getModel(Bundle data) {
        return new RecordList();
    }
}
