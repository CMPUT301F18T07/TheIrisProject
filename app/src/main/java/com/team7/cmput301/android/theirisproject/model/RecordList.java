/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

public class RecordList implements Iterable<Record> {
    private ArrayList<Record> records = new ArrayList<Record>();

    public ArrayList<Record> getRecords() {
        return records;
    }

    public RecordList() {

    }

    public boolean contains(Record record) {
        return records.contains(record);
    }

    public void add(Record record) {
        records.add(record);
    }

    public void remove(Record record) {
        records.remove(record);
    }

    public int length() {
        return records.size();
    }

    @NonNull
    @Override
    public Iterator<Record> iterator() {
        return records.iterator();
    }
}