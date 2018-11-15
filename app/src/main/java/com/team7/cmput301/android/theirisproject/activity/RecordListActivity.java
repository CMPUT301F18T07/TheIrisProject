/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.RecordListAdapter;
import com.team7.cmput301.android.theirisproject.controller.RecordListController;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.task.Callback;

/**
 * Activity for viewing and clicking all Records for a Patient
 *
 * @author anticobalt
 * @see RecordListController
 * @see Callback
 */
public class RecordListActivity extends IrisActivity<RecordList> {

    private RecordListController controller;
    private Toolbar toolbar;
    private ListView recordListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        recordListView = findViewById(R.id.record_item_list);
        controller = createController(getIntent());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // set back button

    }

    @Override
    protected void onStart() {
        super.onStart();
        Callback<RecordList> contCallback = new Callback<RecordList>() {
            @Override
            public void onComplete(RecordList result) {
                render(result);
            }
        };
        controller.getRecords(contCallback);
    }

    /**
     * Inflate Action Bar menu
     * @param menu Menu to inflate
     * @return Must return True to actually show menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record_list, menu);
        return true;
    }

    /**
     * Called when an Action Bar item is clicked
     * https://developer.android.com/training/appbar/actions
     * @param item The selected menu item
     * @return False if normal menu processing is to occur, true otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_records:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when back button clicked
     * @return True if back-up successful
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected RecordListController createController(Intent intent) {
        return new RecordListController(intent);
    }


    /**
     * Display all records by populating adapter and setting adapter to ListView
     * @param records
     */
    public void render(RecordList records) {
        Integer recordItemLayout = R.layout.list_record_item;
        RecordListAdapter adapter = new RecordListAdapter(this, recordItemLayout, records.getRecords());
        recordListView.setAdapter(adapter);
    }
}
