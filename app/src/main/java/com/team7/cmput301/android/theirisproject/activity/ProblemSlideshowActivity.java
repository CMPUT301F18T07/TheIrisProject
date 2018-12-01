/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.team7.cmput301.android.theirisproject.ImageSlideAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.RecordPhotoListController;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.task.Callback;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_slideshow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recordPhotoListController = new RecordPhotoListController(getIntent());

        viewPager = findViewById(R.id.photo_slideshow);
        ImageSlideAdapter adapter = new ImageSlideAdapter(this, recordPhotoListController.getPhotos());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recordPhotoListController.queryRecordPhotos(new Callback<Record>() {
            @Override
            public void onComplete(Record res) {
                render(res);
            }
        });
    }

    private void render(Record newState) {
        ((ImageSlideAdapter)viewPager.getAdapter()).setItems(newState.getRecordPhotos());
        viewPager.getAdapter().notifyDataSetChanged();
    }

}
