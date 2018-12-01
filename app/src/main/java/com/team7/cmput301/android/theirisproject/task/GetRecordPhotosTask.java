/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * GetRecordPhotosTask queries the record photos associated to the problem
 *
 * @author jtfwong
 * */
public class GetRecordPhotosTask extends AsyncTask<String, List<RecordPhoto>, Void> {

    private Callback cb;
    private String queryString = "{\"query\":{\"term\":{\"recordId\": \"%s\"}}}";

    public GetRecordPhotosTask(Callback<List<RecordPhoto>> cb) {
        this.cb = cb;
    }

    @Override
    protected Void doInBackground(String... strings) {
        new GetRecordListTask(new Callback<SearchResult>() {
            @Override
            public void onComplete(SearchResult res) {
                List<RecordPhoto> recordPhotos = new ArrayList<>();
                List<Record> recordList = res.getSourceAsObjectList(Record.class, true);
                for (Record r: recordList) {
                    Search search = new Search.Builder(String.format(queryString, r.getId())).addIndex(IrisProjectApplication.INDEX).addType("recordphoto").build();
                    try {
                        recordPhotos.addAll(IrisProjectApplication.getDB().execute(search).getSourceAsObjectList(RecordPhoto.class, true));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                publishProgress(recordPhotos);
            }
        }).execute(strings[0]);
        return null;
    }

    @Override
    protected void onProgressUpdate(List<RecordPhoto>... recordPhotos) {
        super.onProgressUpdate(recordPhotos);
        this.cb.onComplete(recordPhotos);
    }
}