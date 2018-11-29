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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

/**
 * AddRecordTask is an async task that will dispatch
 * when the user submits a new record and the task
 * will add it to the database returning its id
 *
 * @author itstc
 * */
public class AddRecordTask extends AsyncTask<Record, Void, String> {
    Callback cb;
    public AddRecordTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected String doInBackground(Record... params) {
        try {
            Index add = new Index.Builder(params[0])
                    .index(IrisProjectApplication.INDEX)
                    .type("record")
                    .build();
            String recordId = IrisProjectApplication.getDB().execute(add).getId();
            Bulk bulkAdd = new Bulk
                    .Builder()
                    .addAction(bulkAddRecordPhotos(params[0].getRecordPhotos(), recordId))
                    .defaultIndex(IrisProjectApplication.INDEX)
                    .defaultType("recordphoto")
                    .build();
            IrisProjectApplication.getDB().execute(bulkAdd);
            Thread.sleep(500);
            return recordId;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("Iris", "null records");
        return null;
    }

    private List<Index> bulkAddRecordPhotos(List<RecordPhoto> photos, String refId) {
        List<Index> indices = new ArrayList<>();
        for(RecordPhoto rp: photos) {
            rp.setRecordId(refId);
            rp.setDate(new Date());
            indices.add(new Index.Builder(rp).index(IrisProjectApplication.INDEX).type("recordphoto").build());
        }
        return indices;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        cb.onComplete(res);
    }
}
