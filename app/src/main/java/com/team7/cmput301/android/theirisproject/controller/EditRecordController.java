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
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.DeleteRecordPhotoTask;
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

    private final int FULLSUCCESS = 1;
    private final int PARTIALSUCCESS = 2;
    private final int FAIL = 3;

    private Record record;
    private List<RecordPhoto> oldPhotoList;
    private List<RecordPhoto> photoList;

    public EditRecordController(Intent intent){
        super(intent);
        record = model;
        photoList = model.getRecordPhotos();
        oldPhotoList = new ArrayList<>(photoList);
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
        return photoList;
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
    public int submitRecord(Callback<Boolean> cb, String title, String desc){

        int code = FAIL;

        record.setTitle(title);
        record.setDesc(desc);

        if (IrisProjectApplication.isConnectedToInternet()) {

            new EditRecordTask().execute(record);

            // Compare old list with new list to find out what
            // photos were added/deleted, then modify the elasticsearch
            // database as required.
            for (RecordPhoto oldPhoto : oldPhotoList) {
                if (!photoList.contains(oldPhoto)) {
                    // this photo was deleted
                    new DeleteRecordPhotoTask(cb).execute(oldPhoto);
                }
            }
            for (RecordPhoto photo : photoList) {
                if (!oldPhotoList.contains(photo)) {
                    // this photo was added
                    new AddRecordPhotoTask(cb).execute(photo);
                }
            }

            code = FULLSUCCESS;

        } else {

            // Don't allow save if Record Photos changed, because
            // this case can't be handled
            for (RecordPhoto oldPhoto : oldPhotoList) {
                if (!photoList.contains(oldPhoto)) {
                    return FAIL;
                }
            }
            for (RecordPhoto photo : photoList) {
                if (!oldPhotoList.contains(photo)) {
                    return FAIL;
                }
            }

            IrisProjectApplication.putInUpdateQueue(record);
            code = PARTIALSUCCESS;

        }

        return code;

    }

    public void addRecordPhoto(Bitmap imageBitmap) {
        RecordPhoto p = new RecordPhoto(record.getId(), ImageConverter.scaleBitmapPhoto(imageBitmap, 256, 256));
        record.addRecordPhoto(p);
    }

    public void setBodyLocation(String data_src, float[] data_xies) {
        record.setBodyLocation(new BodyLocation(data_src, data_xies[0], data_xies[1]));
    }

    public void setGeoLocation(double[] location) {
        record.editGeoLocation(location[0], location[1]);
    }

}
