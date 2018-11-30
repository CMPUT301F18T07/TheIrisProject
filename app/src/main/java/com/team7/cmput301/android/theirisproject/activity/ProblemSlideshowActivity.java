package com.team7.cmput301.android.theirisproject.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.team7.cmput301.android.theirisproject.ImageSlideAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.RecordController;
import com.team7.cmput301.android.theirisproject.controller.RecordPhotoListController;

/**
 * Activity that displays a slideshow for all of the record
 * photos associated with a problem
 * Reference: https://www.youtube.com/watch?v=Q2FPDI99-as
 *
 * @author jtfwong
 * */
public class ProblemSlideshowActivity extends AppCompatActivity {

    private RecordPhotoListController recordPhotoListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_slideshow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recordPhotoListController = new RecordPhotoListController(getIntent());

        ViewPager viewPager = findViewById(R.id.photo_slideshow);
        ImageSlideAdapter adapter = new ImageSlideAdapter(this, recordPhotoListController.getPhotos());
        viewPager.setAdapter(adapter);
    }

}
