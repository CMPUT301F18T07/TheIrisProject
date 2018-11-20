package com.team7.cmput301.android.theirisproject.helper;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.io.IOException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * DeleteUserTask is used for testing purposes to delete a User from the DB, given their e-mail
 * Note that this is not involved in any use cases for the application!
 *
 * @author Jmmxp
 */
public class DeleteUserTask extends AsyncTask<String, Void, Boolean> {

    private static final String TAG = DeleteUserTask.class.getSimpleName();
    private Callback<Boolean> callback;

    public DeleteUserTask(Callback<Boolean> callback) {
        this.callback = callback;
    }

    /**
     * Deletes the User registered with the given email
     */
    @Override
    protected Boolean doInBackground(String... strings) {
        String email = strings[0];
        if (email == null) {
            return false;
        }

        Search search = new Search.Builder("{\"query\": {\"term\": {\"email\": \"" + email + "\"}}}")
                .addIndex(IrisProjectApplication.INDEX)
                .addType("user")
                .build();

        JestDroidClient client = IrisProjectApplication.getDB();

        try {
            SearchResult searchResult = client.execute(search);

            if (!searchResult.isSucceeded()) {
                Log.i(TAG, "search failed");
                return false;
            }

            JsonArray arrayHits = searchResult.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits");
            // Make sure there is a hit in the array before trying to delete
            if (arrayHits.size() == 0) {
                return false;
            }

            String id = searchResult.getSourceAsObject(Patient.class, true).getId();
//            id = arrayHits.getAsJsonObject().get("_index").getAsString();

            Delete delete = new Delete.Builder(id)
                    .index(IrisProjectApplication.INDEX).type("user").build();

            JestResult deleteResult = client.execute(delete);

            if (!deleteResult.isSucceeded()) {
                Log.i(TAG, "delete failed");
                return false;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean deleteSuccess) {
        super.onPostExecute(deleteSuccess);
        callback.onComplete(deleteSuccess);
    }
}
