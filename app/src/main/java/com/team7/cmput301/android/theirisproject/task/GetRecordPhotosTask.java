/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import java.io.IOException;
import java.util.List;

import io.searchbox.core.Search;

/**
 * GetRecordPhotosTask queries the record photos associated to the problem
 *
 * @author jtfwong
 * */
public class GetRecordPhotosTask extends AsyncTask<List<Record>, List<RecordPhoto>, Void> {

    private Callback cb;
    private String queryString = "{\n" +
            "\t\"query\": {\n" +
            "\t\t\"term\": {\n" +
            "\t\t\t\"recordId\": \"%s\"\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    public GetRecordPhotosTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected Void doInBackground(List<Record>... params) {
        for (Record record: params[0]) {
            try {
                Search search = new Search.Builder(String.format(queryString, record.getId()))
                        .addIndex(IrisProjectApplication.INDEX)
                        .addType("recordphoto")
                        .build();
                publishProgress(IrisProjectApplication.getDB().execute(search).getSourceAsObjectList(RecordPhoto.class, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(List<RecordPhoto>... recordPhotos) {
        super.onProgressUpdate(recordPhotos);
        for (RecordPhoto p: recordPhotos[0]) p.convertBlobToBitmap();
        this.cb.onComplete(recordPhotos[0]);
    }
}