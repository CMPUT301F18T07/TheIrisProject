/**
 * AddProblemController has methods to allow our AddProblemActivity
 * to interact with the database by POST requesting new problems to it
 *
 * @author itstc
 * */
package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Record;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.SearchResult;
import io.searchbox.params.Parameters;

/**
 * DeleteProblemTask asynchronously deletes a problem from the database
 *
 * @author VinnyLuu
 */
public class DeleteProblemTask extends AsyncTask<String, Void, Boolean> {
    private Callback cb;

    public DeleteProblemTask(Callback cb) {this.cb = cb;}

    /**
     * doInBackground will send a request to DB with edited problem
     * returning a boolean for its status of deletion
     * @param params use only params[0] containing the edited Problem.class
     * @return Boolean: true if successful, else false
     * */
    @Override
    protected Boolean doInBackground(String... params) {

        try {
            new GetRecordListTask(new Callback<SearchResult>() {
                // DELETE ALL RECORDS ASSOCIATED WITH PROBLEM
                @Override
                public void onComplete(SearchResult res) {
                    List<Record> recordList = res.getSourceAsObjectList(Record.class, true);
                    for (Record r : recordList) {
                        new DeleteRecordTask().execute(r.getId());
                    }
                }
            }).execute(params[0]);
            // DELETE PROBLEM
            Delete update = new Delete.Builder(params[0])
                    .index(IrisProjectApplication.INDEX)
                    .type("problem")
                    .build();
            return IrisProjectApplication.getDB().execute(update).isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean res) {
        super.onPostExecute(res);
        cb.onComplete(res);
    }
}
