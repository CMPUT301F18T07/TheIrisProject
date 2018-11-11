package com.team7.cmput301.android.theirisproject;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.Callback;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Problem;

import java.io.IOException;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * GetProblemTask asynchronously gets a problem from the database
 *
 */
public class GetProblemTask extends AsyncTask<String, Void, Problem> {

    private Callback callback;

    public GetProblemTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Problem doInBackground(String... params) {
        SearchResult res = null;
        Problem result = null;
        try {
            Search get = new Search.Builder("{\"query\": {\"term\": {\"user\": \"" + params[0] + "\"}}}")
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("problem")
                    .build();
            res = IrisProjectApplication.getDB().execute(get);
            result = res.getSourceAsObject(Problem.class, true);
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
