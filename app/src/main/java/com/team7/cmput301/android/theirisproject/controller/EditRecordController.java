/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.task.EditRecordTask;

/**
 * Controller for editing a Record
 *
 * @author anticobalt
 * @see com.team7.cmput301.android.theirisproject.activity.EditRecordActivity
 * @see Record
 */
public class EditRecordController extends IrisController<Record> {

    private Record record;

    public EditRecordController(Intent intent){
        super(intent);
        record = model;
    }

    @Override
    Record getModel(Bundle data) {
        String recordId = data.getString(Extras.EXTRA_RECORD_ID);
        return IrisProjectApplication.getRecordById(recordId);
    }

    public String getRecordTitle() {
        return record.getTitle();
    }

    public String getRecordDesc() {
        return record.getDesc();
    }

    /**
     * Updates record locally and attempts to update online.
     * If online fails, return false; true otherwise
     *
     * @param context Required for internet check
     * @param title Record's new title
     * @param desc Record's new description
     * @return Whether update to online was possible or not
     */
    public boolean submitRecord(Context context, String title, String desc){

        Boolean pushedOnline = false;

        record.setTitle(title);
        record.setDesc(desc);

        if (IrisProjectApplication.isConnectedToInternet(context)) {
            new EditRecordTask().execute(record);
            pushedOnline = true;
        } else {
            IrisProjectApplication.putInUpdateQueue(record);
        }

        return pushedOnline;

    }

}
