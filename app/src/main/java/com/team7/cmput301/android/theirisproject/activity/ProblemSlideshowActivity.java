/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.team7.cmput301.android.theirisproject.ImageListAdapter;
import com.team7.cmput301.android.theirisproject.ImageSlideAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.RecordPhotoListController;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.List;

/**
 * Activity that displays a slideshow for all of the record
 * photos associated with a problem
 * Reference: https://www.youtube.com/watch?v=Q2FPDI99-as
 *
 * @author jtfwong
 * */
public class ProblemSlideshowActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private RecordPhotoListController recordPhotoListController;
    private ImageSlideAdapter imageSlideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_slideshow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recordPhotoListController = new RecordPhotoListController(getIntent());

        viewPager = findViewById(R.id.photo_slideshow);
        imageSlideAdapter = new ImageSlideAdapter(this, recordPhotoListController.getPhotos());
        viewPager.setAdapter(imageSlideAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recordPhotoListController.queryRecordPhotos(new Callback<List<RecordPhoto>>() {
            @Override
            public void onComplete(List<RecordPhoto> res) {
                render(res);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home: finish(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void render(List<RecordPhoto> newState) {
        imageSlideAdapter.setItems(newState);
        imageSlideAdapter.notifyDataSetChanged();
    }

}
