package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.ImageConverter;
import com.team7.cmput301.android.theirisproject.model.GeoLocation;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyLocation;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;
import com.team7.cmput301.android.theirisproject.task.AddRecordPhotoTask;
import com.team7.cmput301.android.theirisproject.task.AddRecordTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * AddRecordController handles the models within the
 * AddRecordActivity form. The controllers role is
 * to store the record and photos and
 * also submitting the record to the database
 *
 * @author itstc
 * */
public class AddRecordController extends IrisController<Record> {

    private final int FULLSUCCESS = 1;
    private final int PARTIALSUCCESS = 2;
    private final int FAIL = 3;

    private String userId;
    private String problemId;
    private BodyLocation bodyLocation;
    private List<RecordPhoto> recordPhotos;
    private GeoLocation geoLocation;

    public AddRecordController(Intent intent) {
        super(intent);
        userId = intent.getStringExtra(Extras.EXTRA_USER_ID);
        problemId = intent.getStringExtra(Extras.EXTRA_PROBLEM_ID);
        recordPhotos = new ArrayList<>();
        geoLocation = new GeoLocation(0.0,0.0);
        model = this.getModel(intent.getExtras());
    }

    public String getUserId() {
        // only current user can add a record so return current user id
        return userId;
    }

    public List<RecordPhoto> getRecordPhotos() {return recordPhotos;}

    public void addRecordPhoto(Bitmap photo) {
        recordPhotos.add(new RecordPhoto(ImageConverter.scaleBitmapPhoto(photo, 256, 256)));
    }

    public void setBodyLocation(String src, float[] location) {
        bodyLocation = new BodyLocation(src, location[0], location[1]);
    }

    public int submitRecord(String title, String desc, Callback cb) {

        // Can't queue Record with Record Photos for update if offline, so don't
        if (recordPhotos.size() != 0 && !IrisProjectApplication.isConnectedToInternet()) {
            return FAIL;
        }

        Record submitRecord = new Record(userId,
                problemId, title, desc, geoLocation, bodyLocation, recordPhotos);
        IrisProjectApplication.addRecordToCache(submitRecord);
        IrisProjectApplication.bindRecord(submitRecord);

        if (IrisProjectApplication.isConnectedToInternet()) {

            new AddRecordTask(new Callback<String>() {
                @Override
                public void onComplete(String res) {
                    submitRecord.setId(res);
                    cb.onComplete(res);
                }
            }).execute(submitRecord);
            return FULLSUCCESS;

        } else {

            // Records not initialized with JestID, and isn't generated
            // unless added to elasticsearch, so manually make one
            submitRecord.setId(UUID.randomUUID().toString());
            IrisProjectApplication.putInUpdateQueue(submitRecord);
            cb.onComplete(submitRecord.getId());
            return PARTIALSUCCESS;

        }

    }

    @Override
    Record getModel(Bundle data) {
        return new Record();
    }

    public void addLocation(double[] location) {
        geoLocation.setPosition(location[0], location[1]);
    }

}
