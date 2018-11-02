/*
 * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;

public class Problem {
    private RecordList records;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private String title;
    private String description;
    private BodyPhoto bodyPhoto;

    public RecordList getRecords() {
        return records;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public BodyPhoto getBodyPhoto() {
        return this.bodyPhoto;
    }

    public ArrayList<RecordPhoto> getSlideShowInfo() {
        return null;
    }

    public void Problem(String title, String description, ArrayList<Record> records, BodyPhoto body) {

    }
}
