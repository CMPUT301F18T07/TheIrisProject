/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of Records pertaining a particular Problem
 * Besides doing typical List things like add/delete, handles more advanced
 * actions like self-lookup.
 * 1-1 association with Problem.
 *
 * @see Problem
 * @author Jmmxp
 */
public class RecordList implements Iterable<Record> {

    private transient List<Record> records = new ArrayList<Record>();
    private List<String> record_ids = new ArrayList<>();

    /* Constructors */

    public RecordList() {
    }

    public RecordList(List<Record> records) {
        this.records = records;
    }

    /* Basic getters */

    public List<Record> getRecords() {
        return records;
    }

    /* Basic list operations */

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

    /* Searches */

    public Record getRecordById(String id) {
        for (Record record : records) {
            if (record.getId().equals(id)) {
                return record;
            }
        }
        return null;
    }

    /* Misc */

    @NonNull
    @Override
    public Iterator<Record> iterator() {
        return records.iterator();
    }

}