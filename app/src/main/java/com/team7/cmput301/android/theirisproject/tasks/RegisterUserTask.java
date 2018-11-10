package com.team7.cmput301.android.theirisproject.tasks;

import android.os.AsyncTask;

import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.RegisterActivity;
import com.team7.cmput301.android.theirisproject.model.User;

import java.io.IOException;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.SearchResult;

/**
 * RegisterUserTask is an AsyncTask that asynchronously registers the given user into the
 * database. It is started by RegisterActivity when the user fills in all fields and clicks the
 * register button.
 *
 * @author Jmmxp
 * @see RegisterActivity
 */

public class RegisterUserTask extends AsyncTask<User, Void, Void> {
    @Override
    protected Void doInBackground(User... users) {
        User user = users[0];
        if (user == null) {
            return null;
        }

        JestDroidClient client = IrisProjectApplication.getDB();
        Index index = new Index.Builder(user).index(IrisProjectApplication.INDEX).type("user").build();

        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                user.setId(result.getId());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
