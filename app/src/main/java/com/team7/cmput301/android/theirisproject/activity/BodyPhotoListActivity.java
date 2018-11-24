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
import android.view.MenuItem;
import android.view.View;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.ImageListAdapter;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.BodyPhotoListController;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.List;

/**
 * BodyPhotoListActivity is an activity to view the
 * users body photos to be used in records
 *
 * @author itstc
 * */
public class BodyPhotoListActivity extends IrisActivity<BodyPhoto> {

    private BodyPhotoListController controller;

    private RecyclerView bodyPhotoList;
    private ImageListAdapter<BodyPhoto> bodyPhotoListAdapter;
    private FloatingActionButton addBodyPhotoButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_photos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controller = createController(getIntent());

        bodyPhotoList = findViewById(R.id.body_photo_list);
        addBodyPhotoButton = findViewById(R.id.add_body_photo);

        bodyPhotoListAdapter = new ImageListAdapter(this, controller.getBodyPhotos(), false);
        bodyPhotoList.setAdapter(bodyPhotoListAdapter);
        bodyPhotoList.setLayoutManager(new GridLayoutManager(this, 3));

        addBodyPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchAddBodyPhotoActivity(IrisProjectApplication.getCurrentUser().getId());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.queryBodyPhotos(new Callback<List<BodyPhoto>>() {
            @Override
            public void onComplete(List<BodyPhoto> res) {
                render(res);
            }
        });
    }

    private void dispatchAddBodyPhotoActivity(String id) {
        Intent intent = new Intent(BodyPhotoListActivity.this, AddBodyPhotoActivity.class);
        intent.putExtra(Extras.EXTRA_BODYPHOTO_USER, id);
        startActivity(intent);
    }

    @Override
    protected BodyPhotoListController createController(Intent intent) {
        return new BodyPhotoListController(intent);
    }

    private void render(List<BodyPhoto> state) {
        bodyPhotoListAdapter.setItems(state);
        bodyPhotoListAdapter.notifyDataSetChanged();
    }
}
