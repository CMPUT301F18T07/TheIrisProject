/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

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
        userId = intent.getStringExtra("bodyphoto_user");
    }

    public void queryBodyPhotos() {
    }

    public List<BodyPhoto> getBodyPhotos() {
        return model;
    }

    @Override
    List<BodyPhoto> getModel(Bundle data) {
        return new ArrayList<>();
    }
}
