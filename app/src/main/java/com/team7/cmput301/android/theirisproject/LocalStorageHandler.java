/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Write to and read from file using Gson.
 * Mostly a copy of the CMPUT301 lab tutorial material by Shaiful Chowdhury.
 *
 * @author anticobalt
 */
public class LocalStorageHandler {

    /**
     * Takes a list unknown type items, and saves it in JSON format to file.
     *
     * @param context application context; required for opening file output byte stream
     * @param list Objects to be serialized
     * @param filename Name of file that will hold serialized objects on device
     */
    public void saveListToBackupFile(Context context, List<?> list, String filename) {

        try {

            FileOutputStream fos = context.openFileOutput(filename, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);

            Gson gson = new Gson();
            gson.toJson(list, writer);
            writer.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns file's serialized objects as a List.
     * An example model is provided to determine how to serialize object.
     * The alternative is to have caller functions pass in a raw string that is to be compared,
     * which seems less extendable than containing the string-comparison ugliness to this function.
     *
     * @param context application context; required for opening file input byte stream
     * @param filename Name of file to read from
     * @param model An instance of the model type that is to be serialized
     * @return The list of deserialized objects
     */
    public List loadListFromBackupFile(Context context, String filename, Object model) {

        List objects;

        try {

            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            Gson gson = new Gson();

            // a hack to generate the right type of objects
            Type listType;
            switch(model.getClass().getSimpleName()) {
                case "Problem":
                    listType = new TypeToken<ArrayList<Problem>>(){}.getType();
                    break;
                case "Record":
                    listType = new TypeToken<ArrayList<Record>>(){}.getType();
                    break;
                default:
                    return new ArrayList();
            }

            objects = gson.fromJson(reader, listType);

        } catch (FileNotFoundException e) {
            objects = new ArrayList<>();
            e.printStackTrace();
        }

        return objects;

    }


}
