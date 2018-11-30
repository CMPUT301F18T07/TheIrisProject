/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.content.Context;
import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

/**
 * Periodically checks internet connection, and bulk updates models that have changed
 * when internet connection detected.
 * https://stackoverflow.com/a/44982271
 *
 * @author anticobalt
 * @see IrisProjectApplication
 */
public class BulkUpdateTask extends AsyncTask<List, Void, Boolean> {

    private Callback<Boolean> callback;
    private int sleepDuration = 120000; // 2 minutes

    public BulkUpdateTask(Callback<Boolean> callback) {
        this.callback = callback;
    }

    /**
     * https://stackoverflow.com/q/48804373
     *
     * @param lists The queues of models that need to be uploaded
     * @return Success or failure
     */
    @Override
    protected Boolean doInBackground(List... lists) {

        while (!IrisProjectApplication.isConnectedToInternet()){
            Timer.sleep(sleepDuration);
        }

        List<Index> indexList = new ArrayList<>();
        Bulk update;

        // Update problems
        for (Object model : lists[0]) {
            Problem problem = (Problem) model;
            Index index = new Index.Builder(problem)
                    .id(problem.getId())
                    .index(IrisProjectApplication.INDEX)
                    .type("problem")
                    .build();
            indexList.add(index);
        }
        update = new Bulk.Builder().addAction(indexList).build();
        try {
            IrisProjectApplication.getDB().execute(update);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Update records
        // TODO: have Problems and Records extend/implement something so that this operation can be generalized
        // ( model.getID() is problematic part, every other variable part can be parameterized )
        indexList.clear();
        for (Object model : lists[1]) {
            Record record = (Record) model;
            Index index = new Index.Builder(record)
                    .id(record.getId())
                    .index(IrisProjectApplication.INDEX)
                    .type("record")
                    .build();
            indexList.add(index);
        }
        update = new Bulk.Builder().addAction(indexList).build();
        try {
            IrisProjectApplication.getDB().execute(update);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        callback.onComplete(aBoolean);
    }
}

