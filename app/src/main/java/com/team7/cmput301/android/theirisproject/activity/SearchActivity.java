/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.ProblemListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.ProblemListController;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.ProblemList;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.ArrayList;

/**
 * Activity for searching through problems and records either by keywords, geolocation, and body location.
 *
 * need to create an adapter
 * probs need to extend another model for problems and records
 * in <>
 *
 * @author caboteja
 */
public class SearchActivity extends IrisActivity {

    private static final String TAG = "SearchFragment";

    //widgets
    private ImageView filter_search;
    private EditText search_text;

    //vars
    private ProblemListController problems_controller;
    private ListView search_problems;
    private ListView search_records;
    private ArrayAdapter<String> adapter;

    private ArrayList<Problem> problems_list;
    private ArrayList<Record> records_list;


    //Booleans
    //By default - searching by keywords
    private Boolean search_by_keywords = true;
    private Boolean search_by_geolocation = false;
    private Boolean search_by_bodylocation = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        filter_search = (ImageView) findViewById(R.id.ic_search);
        init();


        search_text = (EditText) findViewById(R.id.input_search);

        problems_controller = new ProblemListController(getIntent());
//        records_controller = new RecordListController(getIntent());

        search_problems = (ListView) findViewById(R.id.search_problems);
        search_records = (ListView) findViewById(R.id.search_records);


        search_problems.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Problem problem = (Problem) search_problems.getItemAtPosition(i);
                Intent intent = new Intent(SearchActivity.this, ViewProblemActivity.class);
                intent.putExtra(Extras.EXTRA_PROBLEM_ID, problem.getId());
                startActivity(intent);
            }
        });

    }

    private void init() {
        search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //Overrides enter button to execute what user is searching for
                if(i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){

                    problems_list = new ArrayList<Problem>();

                    records_list = new ArrayList<Record>();

                    //Turns what user inputs to search into a string
                    String searchString = "";
                    if (!search_text.equals("")) {
                        searchString = searchString + search_text.getText().toString();
                    }

                    //Check to see how user wants to query
                    //Search by keywords in title
                    if (search_by_keywords == true) {

                    }

                    //Search by geolocation
                    if (search_by_geolocation == true) {

                    }

                    //Search by body location
                    if (search_by_bodylocation == true) {

                    }

                }
                return false;
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Checks to see if search by geolocation filter is clicked
        //if geoloocation was previously clicked or not, or if body location was set to true before
        if (id == R.id.search_by_geolocation) {
            if (search_by_geolocation){
                Toast.makeText(SearchActivity.this, "Searching by keywords", Toast.LENGTH_LONG);
                search_by_geolocation = false;
                search_by_keywords = true;
            }
            else {
                Toast.makeText(SearchActivity.this, "Searching by Geolocation", Toast.LENGTH_LONG);
                search_by_keywords = false;
                search_by_bodylocation = false;
                search_by_geolocation = true;
            }
        }
        //Checks to see if search by body location filter is clicked
        //if body location was previously clicked or not, or if geolocation was set to true before
        if (id == R.id.search_by_bodylocation) {
            if (search_by_bodylocation){
                Toast.makeText(SearchActivity.this, "Searching by keywords", Toast.LENGTH_LONG);
                search_by_bodylocation = false;
                search_by_keywords = true;
            }
            else {
                Toast.makeText(SearchActivity.this, "Searching by Body Location", Toast.LENGTH_LONG);
                search_by_keywords = false;
                search_by_geolocation = false;
                search_by_bodylocation = true;
            }
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onStart() {
        super.onStart();
        problems_controller.getUserProblems(new Callback<ProblemList>() {
            @Override
            public void onComplete(ProblemList result) {
                problems_render(result);
            }
        });
    }

    @Override
    protected ProblemListController createController(Intent intent) {
        return new ProblemListController(intent);
    }

    /**
     * problems_render will update the Activity with the new state provided
     * in the arguments of invoking this method
     *
     * @param result new state of model
     * */
    private void problems_render(ProblemList result) {
        ProblemList newState = result;
        search_problems.setAdapter(new ProblemListAdapter(this, R.layout.list_problem_item, newState.getProblems()));
    }




}
