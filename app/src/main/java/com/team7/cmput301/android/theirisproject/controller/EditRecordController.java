/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.ImageConverter;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyLocation;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;
import com.team7.cmput301.android.theirisproject.task.AddRecordPhotoTask;
import com.team7.cmput301.android.theirisproject.task.EditRecordTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for editing a Record
 *
 * @author anticobalt
 * @see com.team7.cmput301.android.theirisproject.activity.EditRecordActivity
 * @see Record
 */
public class EditRecordController extends IrisController<Record> {

    private Record record;
    private List<RecordPhoto> newRecordPhotos = new ArrayList<>();

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

    public List<RecordPhoto> getRecordPhotos() {
        return record.getRecordPhotos();
    }

    public Bitmap getBodyPhotoBitmap() {
        return record.getBodyPhotoBitmap();
    }

    public String getUserId() {
        return IrisProjectApplication.getUserIdByProblemId(record.getProblemId());
    }

    /**
     * Updates record locally and attempts to update online.
     * If online fails, return false; true otherwise
     *
     * @param title Record's new title
     * @param desc Record's new description
     * @return Whether update to online was possible or not
     */
    public boolean submitRecord(String title, String desc){

        Boolean pushedOnline = false;

        record.setTitle(title);
        record.setDesc(desc);

        if (IrisProjectApplication.isConnectedToInternet()) {

            new EditRecordTask().execute(record);

            // Add only the new Record Photos.
            // newRecordPhotos holds ALL photos that were added in activity
            // (including ones that were added then deleted), while the model
            // properly removes deleted photos.
            for (RecordPhoto p : newRecordPhotos) {
                if (record.getRecordPhotos().contains(p)) {
                    new AddRecordPhotoTask().execute(p);
                }
            }

            pushedOnline = true;

        } else {
            IrisProjectApplication.putInUpdateQueue(record);
        }

        return pushedOnline;

    }

    public void addRecordPhoto(Bitmap imageBitmap) {
        RecordPhoto p = new RecordPhoto(record.getId(), ImageConverter.scaleBitmapPhoto(imageBitmap, 256, 256));
        newRecordPhotos.add(p);
        record.addRecordPhoto(p);
    }

    public void setBodyLocation(String data_src, float[] data_xies) {
        record.setBodyLocation(new BodyLocation(data_src, data_xies[0], data_xies[1]));
    }

    public void setGeoLocation(double[] location) {
        record.editGeoLocation(location[0], location[1]);
    }
}
