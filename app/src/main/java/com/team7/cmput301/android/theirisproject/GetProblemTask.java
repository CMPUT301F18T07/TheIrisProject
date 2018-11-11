package com.team7.cmput301.android.theirisproject;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.model.Problem;

import java.io.IOException;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * GetProblemTask asynchronously gets a problem from the database
 *
 */
public class GetProblemTask extends AsyncTask<String, Void, SearchResult> {

    private Callback callback;

    public GetProblemTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected SearchResult doInBackground(String... params) {
        SearchResult res = null;
        try {
            Search get = new Search.Builder("{\"query\": {\"match\": {\"problem_id\": \"" + params[0] + "\"}}}")
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("problem")
                    .build();
            res = IrisProjectApplication.getDB().execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    protected void onPostExecute(SearchResult res) {
        super.onPostExecute(res);
        callback.onComplete(res);
    }
}
