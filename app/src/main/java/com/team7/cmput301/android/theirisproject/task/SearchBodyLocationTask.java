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

/**
 * SearchBodyLocationTask searches the database for problems/records related to a
 * bodyphoto with a label given
 *
 * @author itstc
 * */
public class SearchBodyLocationTask extends AsyncTask<String, List<Record>, Void> {
    private Callback cb;
    private String queryBodyPhotos = "{\n" +
            "\t\"query\": {\n" +
            "\t\t\"bool\": {\n" +
            "\t\t\t\"must\": [\n" +
            "\t\t\t\t{\"term\": {\"user\":\"%s\"}},\n" +
            "\t\t\t\t{\"terms\": {\"label\": %s}}\n" +
            "\t\t\t\t]\n" +
            "\t\t}\n" +
            "\t},\"fields\": []\n" +
            "}";

    private String queryRecords = "{\n" +
            "\t\"query\": {\n" +
            "\t\t\"nested\":{\n" +
            "\t\t\t\"path\": \"bodyLocation\",\n" +
            "\t\t\t\"query\": {\n" +
            "\t\t\t\t\"bool\": {\n" +
            "\t\t\t\t\t\"must\":[\n" +
            "\t\t\t\t\t\t{\"term\": {\"bodyLocation.bodyPhotoId\": \"%s\"}}\n" +
            "\t\t\t\t\t\t]\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    private String keyFormat = "\"%s\"";

    public SearchBodyLocationTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            // query the body photos associated with the user and keyword
            Search photoSearch = new Search.Builder(String.format(queryBodyPhotos, strings[0], decomposeKeyWords(strings[1])))
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("bodyphoto").build();
            List<SearchResult.Hit<BodyPhoto, Void>> photos =  IrisProjectApplication.getDB()
                    .execute(photoSearch).getHits(BodyPhoto.class, true);
            // for every body photo retrieve the records associated to it
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

    private String decomposeKeyWords(String keyword) {
        String res = "[";
        String keys[] = keyword.split(",");
        for (int i = 0; i < keys.length; i++) {
            res = res.concat(String.format(keyFormat, keys[i]));
            if (i != keys.length - 1) res = res.concat(",");
        }
        res = res.concat("]");
        return res;
    }

    @Override
    protected void onProgressUpdate(List<Record>... values) {
        super.onProgressUpdate(values);
        cb.onComplete(values[0]);
    }
}
