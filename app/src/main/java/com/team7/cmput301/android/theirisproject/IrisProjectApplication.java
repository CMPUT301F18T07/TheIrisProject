/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.app.Application;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.ProblemList;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;

import io.searchbox.client.JestClient;
/**
 * IrisProjectApplication is the main class to start our app.
 * This class will initialize all the global states of models
 * which can then allow our activity controllers to populate it.
 *
 *
 * */
public class IrisProjectApplication extends Application {
    // use this index for any request to database
    public static final String INDEX = "cmput301f18t07test";

    // global state of models
    transient private static ProblemList problemList = new ProblemList();
    transient private static Problem problem = new Problem();
    transient private static RecordList recordList = new RecordList();
    transient private static Record record = new Record();

    // our database connection
    transient private static JestClient db = null;

    /**
     * getDB is a function to retrieve the online database
     * if the db variable is currently null we will initialize a
     * new connection to the database.
     * @params {}
     * @return JestClient: our database
     * */
    public static JestClient getDB() {
        // create new JestClient instance if none
        if(db == null) {
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080")
                    .build());
            db = factory.getObject();
        }
        return db;
    }

    public static ProblemList getProblemList() {
        return problemList;
    }

    public static Problem getProblem() {
        return problem;
    }

    public static RecordList getRecordList() {
        return recordList;
    }

    public static Record getRecord() {
        return record;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new GetProblemTask().execute("AWbg-3VzzmnYLEQIgwWh");
    }
}
