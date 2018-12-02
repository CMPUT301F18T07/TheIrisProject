/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.team7.cmput301.android.theirisproject.ProblemListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.RecordListAdapter;
import com.team7.cmput301.android.theirisproject.SearchState;
import com.team7.cmput301.android.theirisproject.controller.SearchController;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchActivity is an activity to search for problems or records based on keyword
 * given in input field.
 *
 * @author itstc
 * @author caboteja
 * */
public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final String defaultOption = "Keywords (default)";
    private final String geoOption = "Geolocation";
    private final String bodyOption = "BodyLocation";

    private SearchController controller;

    private Spinner spinner;

    private EditText searchField;
    private ListView problemsResult;
    private ListView recordsResult;
    private ImageButton submitSearch;

    private ProblemListAdapter problemListAdapter;
    private RecordListAdapter recordListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        controller = new SearchController(getIntent());

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_options, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        searchField = findViewById(R.id.input_search);
        problemsResult = findViewById(R.id.search_problems);
        recordsResult = findViewById(R.id.search_records);

        problemListAdapter = new ProblemListAdapter(SearchActivity.this, R.layout.list_problem_item, new ArrayList<>());
        recordListAdapter = new RecordListAdapter(SearchActivity.this, R.layout.list_record_item, new ArrayList<>());
        problemsResult.setAdapter(problemListAdapter);
        recordsResult.setAdapter(recordListAdapter);

        submitSearch = findViewById(R.id.search_button);
        submitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchField.getText().length() == 0) {
                    Toast.makeText(SearchActivity.this, "Nothing to Search!", Toast.LENGTH_SHORT).show();
                    return;
                }
                controller.querySearch(searchField.getText().toString(), new Callback<List<Record>>() {
                    @Override
                    public void onComplete(List<Record> res) {
                        searchField.setText("");
                        render();
                    }
                });
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch ((String)spinner.getItemAtPosition(i)) {
            case defaultOption:
                controller.setSearchOption(new SearchState.SearchKeyword());
                searchField.setHint(R.string.search_default);
                break;
            case geoOption:
                controller.setSearchOption(new SearchState.SearchGeoLocation());
                searchField.setHint(R.string.search_geolocation);
                break;
            case bodyOption:
                controller.setSearchOption(new SearchState.SearchBodyLocation());
                searchField.setHint(R.string.search_bodylocation);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void render() {
        problemListAdapter.setItems(controller.getProblems());
        recordListAdapter.setItems(controller.getRecords());
    }
}
