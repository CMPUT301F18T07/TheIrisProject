/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Record;

import java.io.IOException;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class SearchBodyLocationTask extends AsyncTask<String, List<Record>, Void> {
    private Callback cb;
    private String queryBodyPhotos = "{\n" +
            "\t\"query\": {\n" +
            "\t\t\"bool\": {\n" +
            "\t\t\t\"must\": [\n" +
            "\t\t\t\t{\"term\": {\"user\":\"%s\"}\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\"term\": {\"label\": \"%s\"}\n" +
            "\t\t\t\t}\n" +
            "\t\t\t\t]\n" +
            "\t\t}\n" +
            "\t},\"fields\": [\"user\", \"label\"]\n" +
            "}";

    private String queryRecords = "{\n" +
            "  \"query\": {\n" +
            "    \"nested\": {\n" +
            "      \"path\": \"bodyLocation\",\n" +
            "      \"query\": {\n" +
            "        \"bool\": {\n" +
            "          \"must\": [\n" +
            "            { \"match\": { \"bodyLocation.bodyPhotoId\": \"%s\" }}\n" +
            "          ]\n" +
            "        }}}}}";

    public SearchBodyLocationTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            Search photoSearch = new Search.Builder(String.format(queryBodyPhotos, strings[0], strings[1]))
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("bodyphoto").build();
            List<SearchResult.Hit<BodyPhoto, Void>> photos =  IrisProjectApplication.getDB()
                    .execute(photoSearch).getHits(BodyPhoto.class, true);
            Log.d("Iris", "size: " + photos.size());
            for (SearchResult.Hit<BodyPhoto,Void> bp: photos) {
                Search recordSearch = new Search.Builder(String.format(queryRecords, bp.id))
                        .addIndex(IrisProjectApplication.INDEX).addType("record").build();
                List<Record> results = IrisProjectApplication.getDB().execute(recordSearch).getSourceAsObjectList(Record.class, true);
                publishProgress(results);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(List<Record>... values) {
        super.onProgressUpdate(values);
        cb.onComplete(values[0]);
    }
}
