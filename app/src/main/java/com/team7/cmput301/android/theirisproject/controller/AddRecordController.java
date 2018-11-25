package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.ImageConverter;
import com.team7.cmput301.android.theirisproject.model.GeoLocation;
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
    private List<RecordPhoto> recordPhotos;
    private GeoLocation geoLocation;

    public AddRecordController(Intent intent) {
        super(intent);
        problemId = intent.getStringExtra(Extras.EXTRA_PROBLEM_ID);
        recordPhotos = new ArrayList<>();
        geoLocation = new GeoLocation(0.0,0.0);
        model = this.getModel(intent.getExtras());
    }

    public List<RecordPhoto> getRecordPhotos() {return recordPhotos;}

    public void addRecordPhoto(Bitmap photo) {
        recordPhotos.add(new RecordPhoto(ImageConverter.scaleBitmapPhoto(photo, 256, 256)));
    }

    public void submitRecord(String text, String desc, Callback cb) {
        Record submitRecord = new Record(problemId, text, desc, geoLocation, recordPhotos);
        new AddRecordTask(cb).execute(submitRecord);
    }

    @Override
    Record getModel(Bundle data) {
        return new Record();
    }

    public void addLocation(double[] location) {
        geoLocation.setPosition(location[0], location[1]);
    }
}
