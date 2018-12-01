/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.TransferCode;

import java.io.IOException;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

public class AddTransferCodeTask extends AsyncTask<TransferCode, Void, Boolean> {
    @Override
    protected Boolean doInBackground(TransferCode... transferCodes) {
        Index post = new Index.Builder(transferCodes[0])
                .index(IrisProjectApplication.INDEX)
                .type("code")
                .build();

        try {
            DocumentResult res = IrisProjectApplication.getDB().execute(post);

            if (res.isSucceeded()) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
