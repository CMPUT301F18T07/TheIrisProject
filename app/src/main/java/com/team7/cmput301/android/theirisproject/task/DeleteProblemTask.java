/**
 * AddProblemController has methods to allow our AddProblemActivity
 * to interact with the database by POST requesting new problems to it
 *
 * @author itstc
 * */
package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;

import java.io.IOException;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;

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
     * @param params: use only params[0] containing the edited Problem.class
     * @return Boolean: true if successful, else false
     * */
    @Override
    protected Boolean doInBackground(String... params) {

        try {
            Delete update = new Delete.Builder(params[0]).index(IrisProjectApplication.INDEX).type("problem").build();
            DocumentResult res = IrisProjectApplication.getDB().execute(update);
            if (res.isSucceeded()) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
