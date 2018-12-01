/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.ImageListAdapter;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.BodyPhotoListController;
import com.team7.cmput301.android.theirisproject.model.BodyLocation;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.List;

/**
 * BodyPhotoListActivity is an activity to view the
 * users body photos to be used in records
 *
 * @author itstc
 * */
public class BodyPhotoListActivity extends IrisActivity<BodyPhoto> implements AddBodyLocationDialogFragment.AddBodyLocationDialogListener {
    private static final int ADD_BODYPHOTO_START = 1;

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

        if (getIntent().getBooleanExtra(Extras.EXTRA_BODYPHOTO_FORM, false)) {
            bodyPhotoListAdapter = new ImageListAdapter(this, controller.getBodyPhotos(), ImageListAdapter.TYPE_BODY_LOCATION_FORM);
        } else {
            bodyPhotoListAdapter = new ImageListAdapter(this, controller.getBodyPhotos(), false);
        }
        bodyPhotoList.setAdapter(bodyPhotoListAdapter);
        bodyPhotoList.setLayoutManager(new GridLayoutManager(this, 3));

        addBodyPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchAddBodyPhotoActivity(IrisProjectApplication.getCurrentUser().getId());
            }
        });

        controller.queryBodyPhotos(new Callback<List<BodyPhoto>>() {
            @Override
            public void onComplete(List<BodyPhoto> res) {
                render(res);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        Bundle extras = data.getExtras();

        if (requestCode == ADD_BODYPHOTO_START
                && resultCode == AddBodyPhotoActivity.RESULT_OK
                && extras.getParcelable("data") != null) {
            // add bodyphoto from AddBodyPhotoActivity and update UI
            controller.addBodyPhoto(extras.getParcelable("data"));
            render(controller.getBodyPhotos());
        }
    }

    /**
     * dispatchAddBodyPhotoActivity will start a new activity
     * to add a body photo. The result will return to this
     * activity.
     *
     * @param id: user's _id
     * */
    private void dispatchAddBodyPhotoActivity(String id) {
        Intent intent = new Intent(BodyPhotoListActivity.this, AddBodyPhotoActivity.class);
        intent.putExtra(Extras.EXTRA_BODYPHOTO_USER, id);
        startActivityForResult(intent, ADD_BODYPHOTO_START);
    }

    @Override
    protected BodyPhotoListController createController(Intent intent) {
        return new BodyPhotoListController(intent);
    }

    private void render(List<BodyPhoto> state) {
        bodyPhotoListAdapter.setItems(state);
        bodyPhotoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFinishAddBodyLocation(BodyLocation location, Bitmap image) {
        Intent intent = new Intent();
        intent.putExtra("data_src", location.getBodyPhotoId());
        intent.putExtra("data_xy", location.getLocation());
        intent.putExtra("data_img", image);
        setResult(RESULT_OK, intent);
        finish();
    }
}
