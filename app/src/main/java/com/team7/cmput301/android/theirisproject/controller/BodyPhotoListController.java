/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.GetBodyPhotoTask;

import java.util.ArrayList;
import java.util.List;

/**
 * BodyPhotoListController handles the modifications and queries
 * of body photos to be displayed on BodyPhotoListActivity
 *
 * @author itstc
 * */
public class BodyPhotoListController extends IrisController<List<BodyPhoto>> {

    private String userId;

    public BodyPhotoListController(Intent intent) {
        super(intent);
        model = getModel(intent.getExtras());
    }

    public void queryBodyPhotos(Callback cb) {

        if (!IrisProjectApplication.isConnectedToInternet()) {
            new GetBodyPhotoTask(new Callback<List<BodyPhoto>>() {
                @Override
                public void onComplete(List<BodyPhoto> res) {
                    model = res;
                    cb.onComplete(res);
                }
            }).execute(userId);
        }

        // Give local data as placeholder, or alternative if internet down
        cb.onComplete(model);

    }

    public List<BodyPhoto> getBodyPhotos() {
        return model;
    }

    public void addBodyPhoto(BodyPhoto bp) {
        model.add(bp);
    }

    @Override
    List<BodyPhoto> getModel(Bundle data) {
        String defaultID = IrisProjectApplication.getCurrentUser().getId();
        userId = data.getString(Extras.EXTRA_BODYPHOTO_USER, defaultID);
        return ((Patient) IrisProjectApplication.getCurrentUser()).getBodyPhotos();
    }
}
