/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.team7.cmput301.android.theirisproject.ImageListAdapter;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.BodyPhotoListController;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

public class BodyPhotoListActivity extends IrisActivity<BodyPhoto> {

    private BodyPhotoListController controller;

    private RecyclerView bodyPhotoList;
    private FloatingActionButton addBodyPhotoButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_photos);

        controller = createController(getIntent());

        bodyPhotoList = findViewById(R.id.body_photo_list);
        addBodyPhotoButton = findViewById(R.id.add_body_photo);

        bodyPhotoList.setAdapter(new ImageListAdapter(this, controller.getBodyPhotos(), false));
        bodyPhotoList.setLayoutManager(new GridLayoutManager(this, 1));

        addBodyPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchAddBodyPhotoActivity(IrisProjectApplication.getCurrentUser().getId());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void dispatchAddBodyPhotoActivity(String id) {
        Intent intent = new Intent(BodyPhotoListActivity.this, AddBodyPhotoActivity.class);
        intent.putExtra("bodyphoto_user", id);
        startActivity(intent);
    }



    @Override
    protected BodyPhotoListController createController(Intent intent) {
        return new BodyPhotoListController(intent);
    }
}
