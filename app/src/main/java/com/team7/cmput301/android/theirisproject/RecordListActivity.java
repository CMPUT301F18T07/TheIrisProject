/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.controller.RecordListController;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.tasks.Callback;

/**
 * Activity for viewing and clicking all Records for a Patient
 * @author anticobalt
 * @see RecordListController
 * @see Callback
 */
public class RecordListActivity extends IrisActivity<RecordList> {

    private RecordListController controller;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        this.controller = createController(getIntent());

        //loadCustomActionBar();
        loadCustomToolbar();

    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.getRecords(new Callback<RecordList>(){
            @Override
            public void onComplete(RecordList result) {
                render(result);
            }
        });
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

    @Override
    protected RecordListController createController(Intent intent) {
        return new RecordListController(intent);
    }

    /**
     * https://stackoverflow.com/a/12388200
     */
    private void loadCustomActionBar() {

        String title = "Records";

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.centered_action_bar);
        getSupportActionBar().setTitle(title);


    }

    /**
     * https://medium.com/@101/android-toolbar-for-appcompatactivity-671b1d10f354
     * https://stackoverflow.com/a/30211080
     */
    private void loadCustomToolbar(){

        String title = "Records";

        toolbar = findViewById(R.id.centered_toolbar);
        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.main_toolbar_title)).setText(title);

    }

    public void render(RecordList records) {

    }
}
