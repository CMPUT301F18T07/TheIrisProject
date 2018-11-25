/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.User;

import java.io.IOException;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

import static com.team7.cmput301.android.theirisproject.model.User.*;

/**
 * Task that is used to update the given username's email and phone number to the newly given
 * email and phone number
 *
 * @author Jmmxp
 */
public class EditProfileTask extends AsyncTask<User, Void, Void> {

    private static final String TAG = EditProfileTask.class.getSimpleName();

    @Override
    protected Void doInBackground(User... users) {
        User updatedUser = users[0];

        String userId = updatedUser.getId();

        Index update;
        update = new Index.Builder(updatedUser).id(userId).index(IrisProjectApplication.INDEX).type("user").build();

        try {
            DocumentResult res = IrisProjectApplication.getDB().execute(update);

            if (!res.isSucceeded()) {
                Log.e(TAG, "Could not update database successfully with user's updated Profile information");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
