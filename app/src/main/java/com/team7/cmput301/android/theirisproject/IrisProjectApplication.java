/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.User;
import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.GetUserDataTask;

import java.util.HashMap;

import io.searchbox.client.JestClient;

/**
 * IrisProjectApplication is the main class to start our app.
 * This class will initialize all the global states of models
 * which can then allow our activity controllers to populate it.
 *
 * @author itstc
 * @author anticobalt
 * */
public class IrisProjectApplication extends Application {

    // use this index for any request to database
    public static final String INDEX = "cmput301f18t07test";

    // our database connection
    transient private static JestDroidClient db = null;
    transient private static User currentUser = null;

    // model caches, for fast retrieval
    private static HashMap<String, Record> records = new HashMap<>();
    private static HashMap<String, Problem> problems = new HashMap<>();

    /**
     * getDB is a function to retrieve the online database
     * if the db variable is currently null we will initialize a
     * new connection to the database.
     * @return JestClient: our database
     * */
    public static JestDroidClient getDB() {
        // create new JestClient instance if none
        if (db == null) {
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080")
                    .multiThreaded(true)
                    .build());
            db = (JestDroidClient) factory.getObject();
        }
        return db;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Determines if currently connected to internet.
     * https://stackoverflow.com/a/32771164
     * @param context The Context that invokes check
     * @return True if connected, false if not
     */
    public static Boolean isConnectedToInternet(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        } else {
            // can't determine if connected to internet, so assume not
            return false;
        }
        return (activeNetwork != null);

    }

    public static void addProblemToCache(Problem problem) {
        problems.put(problem.getId(), problem);
    }

    public static void addRecordToCache(Record record) {
        records.put(record.getId(), record);
    }

    public static Record getRecordById(String id) {

        // try the cache first
        Record record = records.get(id);

        // if nothing found, linear lookup required
        if (record == null) {
            // TODO
            System.out.println("Tried to find " + id);
        }

        return record;

    }

    public static Problem getProblemById(String id) {

        // try the cache first
        Problem problem = problems.get(id);

        // if nothing found, linear lookup required
        if (problem == null) {
            // TODO
            System.out.println("Tried to find " + id);
        }

        return problem;

    }
}
