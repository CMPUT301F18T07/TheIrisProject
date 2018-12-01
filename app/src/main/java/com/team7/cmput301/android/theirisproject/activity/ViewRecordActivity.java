/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.ImageListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.RecordController;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.ArrayList;

/**
 * ViewRecordActivity used to view a record
 *
 * @author jtfwong
 * */
public class ViewRecordActivity extends AppCompatActivity {

    private Fragment enlargeImageFragment;

    private TextView title;
    private TextView desc;
    private TextView date;

    private RecyclerView recordPhotos;
    private ImageListAdapter<RecordPhoto> recordPhotoAdapter;

    private RecordController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controller = new RecordController(getIntent());

        title = findViewById(R.id.record_title);
        desc = findViewById(R.id.record_description);
        date = findViewById(R.id.record_date);

        recordPhotos = findViewById(R.id.record_photos);
        recordPhotoAdapter = new ImageListAdapter(this, new ArrayList(), false);
        recordPhotos.setAdapter(recordPhotoAdapter);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 3);
        recordPhotos.setLayoutManager(gridLayout);

        render(controller.getRecordModel());
    }

    // finish activity on back arrow clicked in action bar
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
        controller.getRecordData(new Callback<Record>() {
            @Override
            public void onComplete(Record res) {
                render(res);
            }
        });
    }

    private void render(Record newState) {
        title.setText(newState.getTitle());
        desc.setText(newState.getDesc());
        date.setText(newState.getDate().toString());
        recordPhotoAdapter.setItems(newState.getRecordPhotos());
        recordPhotoAdapter.notifyDataSetChanged();
    }
}