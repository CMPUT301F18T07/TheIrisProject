/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.task.AddBodyPhotoTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

public class AddBodyPhotoController extends IrisController<Bitmap> {

    private String userId;

    public AddBodyPhotoController(Intent intent) {
        super(intent);
        String intentId = intent.getStringExtra(Extras.EXTRA_BODYPHOTO_USER);
        if (intentId == null) userId = IrisProjectApplication.getCurrentUser().getId();
        else userId = intentId;
    }

    public void submitBodyPhoto(String label, Callback cb) {
        if (model != null) new AddBodyPhotoTask(cb).execute(new BodyPhoto(userId, model, label));
    }

    public Bitmap getBodyPhoto() {
        return model;
    }

    public void setBodyPhoto(Bitmap image) {
        model = image;
    }

    @Override
    Bitmap getModel(Bundle data) {
        return null;
    }
}
