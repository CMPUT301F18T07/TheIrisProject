/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.RecordListAdapter;
import com.team7.cmput301.android.theirisproject.controller.RecordListController;
import com.team7.cmput301.android.theirisproject.model.Record;
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
    private Boolean doEditRecords = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controller = createController(getIntent());
        recordListView = findViewById(R.id.record_item_list);

        // Depending on current state, clicks on Records will show them or edit them
        recordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Class dest;
                if (!doEditRecords) {
                    dest = ViewRecordActivity.class;
                }
                else {
                    dest = EditRecordActivity.class;
                    // reset menu state
                    invalidateOptionsMenu();
                    doEditRecords = false;
                }

                Record record = (Record) recordListView.getItemAtPosition(i);
                Intent intent = new Intent(RecordListActivity.this, dest);
                intent.putExtra(Extras.EXTRA_RECORD_ID, record.getId());
                startActivity(intent);

            }
        });

    }

    /**
     * Re-render every time activity is returned to
     */
    @Override
    protected void onStart() {
        super.onStart();
        Callback<RecordList> contCallback = new Callback<RecordList>() {
            @Override
            public void onComplete(RecordList result) {
                render(result);
            }
        };
        controller.fillRecords(contCallback);
    }

    /**
     * Inflate Toolbar menu
     * @param menu Menu to inflate
     * @return Must return True to actually show menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Called when an Toolbar item is clicked
     * https://developer.android.com/training/appbar/actions
     * https://stackoverflow.com/a/40338826
     * @param item The selected menu item
     * @return False if normal menu processing is to occur, true otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.record_list_action_edit:
                if (!doEditRecords) {
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_mode_edit_blue_24dp));
                    doEditRecords = true;
                }
                else {
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_mode_edit_white_24dp));
                    doEditRecords = false;
                }
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
     * @param records Update-to-date RecordList
     */
    public void render(RecordList records) {
        Integer recordItemLayout = R.layout.list_record_item;
        RecordListAdapter adapter = new RecordListAdapter(this, recordItemLayout, records.asList());
        recordListView.setAdapter(adapter);
    }

}
