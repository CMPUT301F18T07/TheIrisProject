/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.model.Record;

import java.util.List;

/**
 * Adapter for displaying Records in RecordListActivity
 *
 * @author anticobalt
 */
class RecordListAdapter extends ArrayAdapter<Record> {

    private int resource;
    private Activity context;
    private List<Record> records;

    public RecordListAdapter(Activity context, int resource, List<Record> objects) {
        super(context, resource, objects);
        this.context = context;
        this.records = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View problem = inflater.inflate(resource, parent, false);

        // views to populate
        TextView title = problem.findViewById(R.id.record_item_title);
        TextView id = problem.findViewById(R.id.record_item_id;
        TextView text = problem.findViewById(R.id.record_item_text);

        // populate with data given from problems
        title.setText(records.get(position).getTitle());
        id.setText(records.get(position).getId());
        text.setText(records.get(position).getText());
        return problem;
    }

}
