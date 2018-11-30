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
import com.team7.cmput301.android.theirisproject.task.AddRecordTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * AddRecordController handles the models within the
 * AddRecordActivity form. The controllers role is
 * to store the record and photos and
 * also submitting the record to the database
 *
 * @author itstc
 * */
public class AddRecordController extends IrisController<Record>{
    private String problemId;
    private BodyLocation bodyLocation;
    private List<RecordPhoto> recordPhotos;

    public AddRecordController(Intent intent) {
        super(intent);
        problemId = intent.getStringExtra(Extras.EXTRA_PROBLEM_ID);
        recordPhotos = new ArrayList<>();
        model = this.getModel(intent.getExtras());
    }

    public List<RecordPhoto> getRecordPhotos() {return recordPhotos;}

    public void addRecordPhoto(Bitmap photo) {
        recordPhotos.add(new RecordPhoto(ImageConverter.scaleBitmapPhoto(photo, 256, 256)));
    }

    public void setBodyLocation(String src, float[] location) {
        bodyLocation = new BodyLocation(src, location[0], location[1]);
    }

    public Boolean submitRecord(String title, String desc, Callback cb) {

        if (!IrisProjectApplication.isConnectedToInternet()) return false;

        Record submitRecord = new Record(problemId, title, desc, bodyLocation, recordPhotos);

        new AddRecordTask(new Callback<String>() {
            @Override
            public void onComplete(String res) {
                // add result to singleton
                submitRecord.setId(res);
                IrisProjectApplication.addRecordToCache(submitRecord);
                IrisProjectApplication.getProblemById(submitRecord.getProblemId()).addRecord(submitRecord);
                cb.onComplete(res);

            }
        }).execute(submitRecord);

        return true;

    }

    @Override
    Record getModel(Bundle data) {
        return new Record();
    }
}
