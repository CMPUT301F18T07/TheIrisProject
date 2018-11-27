/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.model.GeoLocation;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.GetRecordListTask;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.SearchResult;

/**
 * AllGeolocationsController collects all record's geolocations and record titles.
 * Uses GetRecordsTask and takes after RecordListController, changing the return on the
 * onComplete method
 * @see RecordListController
 * @author VinnyLuu
 */
public class AllGeoLocationsController extends IrisController {

    private String problemId;
    private List<Record> records;
    private Callback<SearchResult> taskCallback;

    public AllGeoLocationsController(Intent intent) {
        super(intent);
        problemId = intent.getStringExtra(Extras.EXTRA_PROBLEM_ID);
    }

    public void getGeolocation(Callback contCallback){

        // Make the task callback
        taskCallback = new Callback<SearchResult>(){
            /* When complete, convert the search results into RecordList,
             * save, then prompt update of views
             */
            @Override
            public void onComplete(SearchResult res) {
                RecordList results = new RecordList(res.getSourceAsObjectList(Record.class, true));
                records = results.asList();
                List<GeoLocation> locations = new ArrayList<GeoLocation>();
                List<String> titles = new ArrayList<String>();
                for (Record aRecord : records) {
                    locations.add(aRecord.getGeoLocation());
                    titles.add(aRecord.getTitle());
                }
                List<Object> result = new ArrayList<Object>();
                result.add(locations);
                result.add(titles);
                contCallback.onComplete(result);
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
