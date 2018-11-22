/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.ImageListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.RecordController;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.task.Callback;

/**
 * ViewRecordActivity used to view a record
 *
 * @author jtfwong
 * */
public class ViewRecordActivity extends AppCompatActivity {

    private TextView title;
    private TextView desc;
    private TextView date;

    private RecyclerView recordPhotos;

    private RecordController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new RecordController(getIntent());

        title = findViewById(R.id.record_title);
        desc = findViewById(R.id.record_description);
        date = findViewById(R.id.record_date);

        recordPhotos = findViewById(R.id.record_photos);
        recordPhotos.setAdapter(new ImageListAdapter(controller.getPhotos(), false));
        GridLayoutManager gridLayout = new GridLayoutManager(this, 1);
        recordPhotos.setLayoutManager(gridLayout);
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
        ((ImageListAdapter)recordPhotos.getAdapter()).setItems(newState.getRecordPhotos());
        recordPhotos.getAdapter().notifyDataSetChanged();
    }
}