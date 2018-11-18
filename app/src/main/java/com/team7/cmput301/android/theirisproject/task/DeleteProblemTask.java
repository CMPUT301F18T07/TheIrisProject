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


    @Override
    protected Boolean doInBackground(String... params) {

        try {
            Delete update = new Delete.Builder(params[0]).index(IrisProjectApplication.INDEX).type("problem").build();
            DocumentResult res = IrisProjectApplication.getDB().execute(update);
            if (res.isSucceeded()) {
                return true;
            }
            else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
