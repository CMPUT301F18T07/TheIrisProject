package com.team7.cmput301.android.theirisproject.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.team7.cmput301.android.theirisproject.ImageSlideAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.ProblemController;
import com.team7.cmput301.android.theirisproject.controller.RecordController;

public class ProblemSlideshowActivity extends AppCompatActivity {

    private RecordController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_slideshow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new RecordController(getIntent());

        ViewPager viewPager = findViewById(R.id.photo_slideshow);
        ImageSlideAdapter adapter = new ImageSlideAdapter(this, controller.getPhotos());
        viewPager.setAdapter(adapter);
    }

}
