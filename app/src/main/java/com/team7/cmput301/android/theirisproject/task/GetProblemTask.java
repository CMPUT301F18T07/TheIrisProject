/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.model.Problem;

import java.io.IOException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Search;

import static com.team7.cmput301.android.theirisproject.IrisProjectApplication.INDEX;

/**
 * GetProblemTask asynchronously gets a problem from the database
 *
 * @author VinnyLuu
 */
public class GetProblemTask extends AsyncTask<String, Void, Problem> {

    private Callback callback;

    public GetProblemTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Problem doInBackground(String... params) {
        Problem result = null;
        try {
            Get get = new Get.Builder(INDEX,params[0])
                    .type("problem")
                    .build();
            JestResult res = IrisProjectApplication.getDB().execute(get);
            result = res.getSourceAsObject(Problem.class);

            Search bodyPhotoSearch = new Search.Builder("{\"query\": {\"term\": {\"problemId\": \"" + params[0] + "\"}}}")
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("bodyphoto")
                    .build();
            res = IrisProjectApplication.getDB().execute(bodyPhotoSearch);
            result.setBodyPhotos(res.getSourceAsObjectList(BodyPhoto.class, true));

            for (BodyPhoto bp: result.getBodyPhotos()) {
                Log.d("Iris", bp.getId());
                bp.convertBlobToPhoto();
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Problem res) {
        super.onPostExecute(res);
        callback.onComplete(res);
    }
}
