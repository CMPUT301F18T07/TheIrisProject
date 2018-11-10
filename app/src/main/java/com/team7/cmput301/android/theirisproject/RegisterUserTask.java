package com.team7.cmput301.android.theirisproject;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.model.User;

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

        Index index = new Index.Builder(user).index("");

        return null;
    }
}
