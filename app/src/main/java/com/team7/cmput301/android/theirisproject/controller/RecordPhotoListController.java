/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.GetRecordListTask;
import com.team7.cmput301.android.theirisproject.task.GetRecordPhotoTask;
import com.team7.cmput301.android.theirisproject.task.GetRecordPhotosTask;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.SearchResult;
import io.searchbox.core.search.sort.Sort;

/**
 * RecordPhotoListController's main purpose is to return a list of
 * all record photos associated with a problem
 *
 * @author jtfwong
 * */
public class RecordPhotoListController extends IrisController<List<RecordPhoto>> {

    private String problemId;

    public RecordPhotoListController(Intent intent) {
        super(intent);
        problemId = intent.getStringExtra(Extras.EXTRA_PROBLEM_ID);
        model = getModel(intent.getExtras());
    }

    /**
     * queryRecordPhotos executes GetRecordPhotosTask and updates
     * the model in controller and also invoking the callback
     * given from activity
     * @param cb
     * */
    public void queryRecordPhotos(Callback cb) {
        new GetRecordListTask(new Callback<SearchResult>() {
            @Override
            public void onComplete(SearchResult res) {
                List<Record> records = res.getSourceAsObjectList(Record.class, true);
                new GetRecordPhotosTask(new Callback<List<RecordPhoto>>() {
                    @Override
                    public void onComplete(List<RecordPhoto> results) {
                        model.addAll(results);
                        cb.onComplete(model);
                    }
                }).execute(records);
            }
        }, new Sort("date", Sort.Sorting.ASC)).execute(problemId);
    }

    public List<RecordPhoto> getPhotos() { return model; }


    @Override
    List<RecordPhoto> getModel(Bundle data) {
        List<RecordPhoto> photos = new ArrayList<>();
        try {
            RecordList records = IrisProjectApplication.getProblemById(problemId).getRecords();
            for(Record record: records) {
                photos.addAll(record.getRecordPhotos());
            }
        }catch(NullPointerException e) {
        }
        return photos;
    }
}
