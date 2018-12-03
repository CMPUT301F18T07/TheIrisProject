/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.params.Parameters;

/**
 * SearchProblemsByRecordsTask gets a list of records and will return
 * a list of all problems associated with the records
 *
 * @author itstc
 * */
public class SearchProblemsByRecordsTask extends AsyncTask<List<Record>, Void, List<Problem>> {
    private String querySearch = "{\n" +
            "  \"query\": {\n" +
            "    \"terms\": {\n" +
            "      \"_id\": %s\n" +
            "    }\n" +
            "  }\n" +
            "}";
    private Callback cb;
    public SearchProblemsByRecordsTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected List<Problem> doInBackground(List<Record>... params) {
        try {
            Search search = new Search.Builder(String.format(querySearch, buildIdArgument(params[0])))
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("problem")
                    .setParameter(Parameters.SIZE, IrisProjectApplication.SIZE)
                    .build();
            return IrisProjectApplication.getDB().execute(search).getSourceAsObjectList(Problem.class, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private String buildIdArgument(List<Record> records) {
        String args = "[";
        String format = "\"%s\"";
        for (int i = 0; i < records.size(); i++) {
            args = args.concat(String.format(format, records.get(i).getProblemId()));
            if (i != records.size()-1) args = args.concat(",");
        }
        args = args.concat("]");
        return args;
    }

    @Override
    protected void onPostExecute(List<Problem> problems) {
        super.onPostExecute(problems);
        cb.onComplete(problems);
    }
}
