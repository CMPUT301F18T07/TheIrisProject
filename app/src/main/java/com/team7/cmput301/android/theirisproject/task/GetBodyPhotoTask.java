/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.params.Parameters;

/**
 * GetBodyPhotoTask will asynchronously retrieve body photos that
 * are related to the user. After querying the response it will
 * invoke the callback given to it during instantiation
 *
 * @author itstc
 * */
public class GetBodyPhotoTask extends AsyncTask<String, Void, List<BodyPhoto>> {
    private String searchQuery = "{\"query\":{\"term\":{\"user\": \"%s\"}}}";
    private Callback cb;
    public GetBodyPhotoTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected List<BodyPhoto> doInBackground(String... params) {
        try {
            Search search = new Search
                    .Builder(String.format(searchQuery, params[0]))
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("bodyphoto")
                    .setParameter(Parameters.SIZE, IrisProjectApplication.SIZE)
                    .build();
            SearchResult response = IrisProjectApplication.getDB().execute(search);
            List<BodyPhoto> result = response.getSourceAsObjectList(BodyPhoto.class, true);
            for(BodyPhoto bp: result) bp.convertBlobToPhoto();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<BodyPhoto> res) {
        super.onPostExecute(res);
        cb.onComplete(res);
    }
}
