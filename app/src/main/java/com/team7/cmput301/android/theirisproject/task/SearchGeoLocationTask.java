/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.params.Parameters;

/**
 * SearchGeoLocationTask searches for Records and their associated Problems with a given
 * latitude,longitude input
 *
 * @author Jmmxp
 */
public class SearchGeoLocationTask extends AsyncTask<String, List<Record>, Void> {

    private Callback callback;

    public SearchGeoLocationTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String userId = strings[0];

        double lat;
        double lon;
        try {
            lat = Double.valueOf(strings[1]);
            lon = Double.valueOf(strings[2]);
        } catch (NumberFormatException exc) {
            return null;
        }


        String queryByGeoLocation = "{  \n" +
                "   \"query\":{  \n" +
                "      \"filtered\":{  \n" +
                "         \"query\":{  \n" +
                "            \"term\":{  \n" +
                "               \"user\":\"" + userId + "\"\n" +
                "            }\n" +
                "         },\n" +
                "         \"filter\":{  \n" +
                "            \"geo_distance\":{  \n" +
                "               \"distance\":\"25km\",\n" +
                "               \"location\":[  \n" +
                "                  " + lon + ",\n" +
                "                  " + lat + "\n" +
                "               ]\n" +
                "            }\n" +
                "         }\n" +
                "      }\n" +
                "   }\n" +
                "}";

        JestDroidClient client = IrisProjectApplication.getDB();

        Search geoSearch = new Search.Builder(queryByGeoLocation)
                .addIndex(IrisProjectApplication.INDEX)
                .setParameter(Parameters.SIZE, IrisProjectApplication.SIZE)
                .addType("record")
                .build();

        try {
            SearchResult searchResult = client.execute(geoSearch);
            if (searchResult.isSucceeded()) {
                List<Record> records = searchResult.getSourceAsObjectList(Record.class, true);
                onProgressUpdate(records);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(List<Record>... values) {
        super.onProgressUpdate(values);
        callback.onComplete(values[0]);
    }
}
