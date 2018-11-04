/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Problem extends IrisModel {
    private RecordList records;
    private List<Comment> comments = new ArrayList<>();
    private String title;
    private String desc;
    private String user;
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
        return this.desc;
    }

    public String getUser() {return this.user;}

    public void setProblem(String title, String desc, String user) {
        this.title = title;
        this.desc = desc;
        this.user = user;
    }

    public List<BodyPhoto> getBodyPhotos() {
        return this.bodyPhotos;
    }

    public List<RecordPhoto> getSlideShowInfo() {
        return null;
    }

    public Problem(String title, String description, RecordList records, List<BodyPhoto> body_photos) {

    }
    public Problem() {

    }

    @Override
    public void updateViews() {
        Log.d("Problem Title", getTitle());
        Log.d("Problem Description", getDescription());
        Log.d("Problem User", getUser());
    }
}
