/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import java.util.ArrayList;
import java.util.List;

public class Problem {
    private RecordList records;
    private List<Comment> comments = new ArrayList<>();
    private String title;
    private String description;
    private List<BodyPhoto> bodyPhotos;

    public RecordList getRecords() {
        return records;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public List<BodyPhoto> getBodyPhotos() {
        return this.bodyPhotos;
    }

    public List<RecordPhoto> getSlideShowInfo() {
        return null;
    }

    public Problem(String title, String description, RecordList records, List<BodyPhoto> body_photos) {

    }
}
