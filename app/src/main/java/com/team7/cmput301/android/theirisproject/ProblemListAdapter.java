/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.model.Problem;

import java.util.List;

/**
 * ProblemListAdapter converts our list of problems to views that can
 * be displayed on the activity.
 * NOTE: currently hacked together, polish later
 *
 * @author itstc
 * */
public class ProblemListAdapter extends ArrayAdapter<Problem> {

    private int resource;
    private Activity context;
    private List<Problem> problems;

    public ProblemListAdapter(Activity context, int resource, List<Problem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.problems = objects;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View problem = inflater.inflate(resource, parent, false);

        // views to populate
        TextView title = problem.findViewById(R.id.problem_item_title);
        TextView id = problem.findViewById(R.id.problem_item_id);
        TextView desc = problem.findViewById(R.id.problem_item_desc);

        // populate with data given from problems
        title.setText(problems.get(position).getTitle());
        id.setText(problems.get(position).getId());
        desc.setText(problems.get(position).getDescription());
        return problem;
    }
}
