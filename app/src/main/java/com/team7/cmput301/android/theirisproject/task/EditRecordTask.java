/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Record;

import java.io.IOException;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Tries to update specified Record in elasticsearch using its ID.
 * Returns true on success, false otherwise.
 *
 * @author anticobalt
 */
public class EditRecordTask extends AsyncTask<Record, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Record... records) {

        Record record = records[0];

        double lon = record.getGeoLocation().asDouble()[1];
        double lat = record.getGeoLocation().asDouble()[0];

        double[] location = { lon, lat };
        record.setLocation(location);
        
        try {
            Index update = new Index.Builder(record).id(record.getId()).index(IrisProjectApplication.INDEX).type("record").build();
            DocumentResult res = IrisProjectApplication.getDB().execute(update);
            return res.isSucceeded();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
