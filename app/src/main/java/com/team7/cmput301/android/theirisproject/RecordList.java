/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

public class RecordList implements Iterable<Record> {
    private ArrayList<Record> records = new ArrayList<Record>();

    public ArrayList<Record> getRecords() {
        return this.records;
    }

    public void RecordList() {

    }

    public boolean contains(Record record) {
        return false;
    }

    public void add(Record record) {

    }

    public void remove (Record record) {

    }

    public int length() {
        return 0;
    }

    @NonNull
    @Override
    public Iterator<Record> iterator() {
        return records.iterator();
    }
}
