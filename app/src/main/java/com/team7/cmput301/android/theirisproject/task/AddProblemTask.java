/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Problem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

import static com.team7.cmput301.android.theirisproject.IrisProjectApplication.INDEX;

/**
 * AddProblemTask is an AsyncTask that sends a POST request to our
 * database with the new problem data. At the end, onPostExecute will
 * either return true/false to the callback
 *
 * @author itstc
 * */
public class AddProblemTask extends AsyncTask<Problem, Void, String> {

    private Callback cb;
    public AddProblemTask(Callback cb) {
        this.cb = cb;
    }

    /**
     * doInBackground will send a request to DB with given new problem
     * returning a boolean for its status
     * @param params use only params[0] containing a new Problem.class
     * @return Boolean: true if successful, else false
     * */
    @Override
    protected String doInBackground(Problem... params) {
        try {
            Index post = new Index
                    .Builder(params[0])
                    .index(IrisProjectApplication.INDEX)
                    .type("problem")
                    .build();
            DocumentResult res = IrisProjectApplication.getDB().execute(post);
            if (res.isSucceeded()) {
                Bulk postBodyPhoto = new Bulk.Builder()
                        .defaultIndex(IrisProjectApplication.INDEX)
                        .defaultType("bodyphoto")
                        .addAction(bulkAddPhotos(params[0].getBodyPhotos(), res.getId()))
                        .build();
                IrisProjectApplication.getDB().execute(postBodyPhoto);
                Thread.sleep(500);
                return res.getId();
            }
            else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * bulkAddPhotos sets up the bulk add to the database by
     * setting all bodyphotos as Index in Jest to execute
     *
     * @param photos
     * */
    private List<Index> bulkAddPhotos(List<BodyPhoto> photos, String problemId) {
        List<Index> res = new ArrayList<>();
        for (BodyPhoto bp: photos) {
            bp.setProblemId(problemId);
            res.add(new Index.Builder(bp).index(IrisProjectApplication.INDEX).type("bodyphoto").build());
        }
        return res;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        cb.onComplete(res);
    }
}
