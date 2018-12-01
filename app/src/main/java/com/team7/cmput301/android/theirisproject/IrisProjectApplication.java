/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.LruCache;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.model.User;
import com.searchly.jestdroid.JestDroidClient;
import com.team7.cmput301.android.theirisproject.task.BulkUpdateTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    // queues and handlers for models that need to have changes uploaded
    private static BulkUpdateTask updater;
    private static Boolean updaterRunning = false;
    private static List<Record> recordUpdateQueue = new ArrayList<>();
    private static List<Problem> problemUpdateQueue = new ArrayList<>();
    private static LocalStorageHandler fileHandler = new LocalStorageHandler();
    private static final String problemUpdateBackupName = "updated_problems.json";
    private static final String recordUpdateBackupName = "updated_records.json";

    // model caches, for fast retrieval
    private static HashMap<String, Record> records = new HashMap<>();
    private static HashMap<String, Problem> problems = new HashMap<>();

    // application context
    private static Context appContext;

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

    public static void setApplicationContext(Context context) {
        appContext = context;
    }

    /**
     * Method used to add current username to SharedPreferences, that is used for testing purposes
     * Used this Android reference https://developer.android.com/training/data-storage/shared-preferences#java
     */
    public static void loginCurrentUser(String username) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(appContext.getString(R.string.shared_pref_username_key), username);
        editor.commit();
    }

    /**
     * Method used to remove current username from SharedPreferences, that is used for testing purposes
     */
    public static void logoutCurrentUser() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(appContext.getString(R.string.shared_pref_username_key));
        editor.commit();
    }

    /**
     * Determines if currently connected to internet.
     * https://stackoverflow.com/a/32771164
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

    /**
     * Prepare for updating in response to internet connection loss.
     * To ensure data online is up-to-date, push non-synchronized model data, if applicable.
     *
     */
    public static void initBulkUpdater() {

        // make the updater
        if (updater == null) {

            Callback<Boolean> cb = new Callback<Boolean>() {
                @Override
                public void onComplete(Boolean res) {
                    handleBulkUpdateResult(res);
                }
            };

            updater = new BulkUpdateTask(appContext, cb);

        }

    }

    /**
     * Put the specified model in appropriate update queue (if not already in there),
     * and execute the updater task if not already running.
     *
     * @param model A model instance that wasn't successfully uploaded to elasticsearch
     */
    public static void putInUpdateQueue(Object model) {

        if (model instanceof Problem) {
            if (!problemUpdateQueue.contains(model)) {
                problemUpdateQueue.add((Problem) model);
                fileHandler.saveListToBackupFile(appContext, problemUpdateQueue, problemUpdateBackupName);
            }
        }
        else if (model instanceof Record) {
            if (!recordUpdateQueue.contains(model)) {
                recordUpdateQueue.add((Record) model);
                fileHandler.saveListToBackupFile(appContext, recordUpdateQueue, recordUpdateBackupName);
            }
        }
        else {
            System.err.println("Trying to put unhandled type into update queue!");
        }

        // queues should still be updated even if they aren't re-passed as arguments
        // i.e. reference to queues is passed
        if (!updaterRunning) {
            updaterRunning = true;
            updater.execute(problemUpdateQueue, recordUpdateQueue);
        }

    }

    /**
     * Get the backups of the update queues and push their data to the elasticsearch.
     *
     * @param callback The callback that defines that occurs after update finished.
     */
    public static void flushUpdateQueueBackups(Callback<Boolean> callback) {

        BulkUpdateTask updater = new BulkUpdateTask(appContext, callback);
        LocalStorageHandler fileHandler = new LocalStorageHandler();

        // Bulk update with backups
        updater.execute(
                fileHandler.loadListFromBackupFile(
                        appContext,
                        problemUpdateBackupName,
                        new Problem()
                ),
                fileHandler.loadListFromBackupFile(
                        appContext,
                        recordUpdateBackupName,
                        new Record()
                )
        );

        // clear the backups
        fileHandler.saveListToBackupFile(appContext, new ArrayList<>(), problemUpdateBackupName);
        fileHandler.saveListToBackupFile(appContext, new ArrayList<>(), recordUpdateBackupName);

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

    /**
     * If bulk update successful, reset everything and remake the updater,
     * so that it can be executed again if internet goes down again.
     *
     * @param success True if bulk update finished, false otherwise
     */
    private static void handleBulkUpdateResult(Boolean success) {
        if (success) {

            // reset objects
            problemUpdateQueue.clear();
            recordUpdateQueue.clear();
            updaterRunning = false;

            // reset files
            fileHandler.saveListToBackupFile(appContext, problemUpdateQueue, problemUpdateBackupName);
            fileHandler.saveListToBackupFile(appContext, recordUpdateQueue, recordUpdateBackupName);

        } else {
            // occurs when connection restored, task tries to upload, but connection lost mid-upload
            // (or something else causes the upload to elasticsearch to fail)
            System.err.println("Connection unstable while trying to upload updates; trying to connect again.");
            updater.execute(problemUpdateQueue, recordUpdateQueue);
        }
    }

}
