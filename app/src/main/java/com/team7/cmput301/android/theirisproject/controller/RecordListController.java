/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.activity.RecordListActivity;
import com.team7.cmput301.android.theirisproject.task.GetRecordListTask;

/**
 * Controller for RecordListActivity
 *
 * @author anticobalt
 * @see RecordListActivity
 * @see GetRecordListTask
 */
public class RecordListController extends IrisController<RecordList> {

    private RecordList records;

    public RecordListController(Intent intent){
        super(intent);
        records = model; // aliasing for clarity
    }

    public RecordList getRecords(){
        return records;
    }

    @Override
    RecordList getModel(Bundle data) {
        String problemId = data.getString(Extras.EXTRA_PROBLEM_ID);
        return IrisProjectApplication.getProblemById(problemId).getRecords();
    }

}
