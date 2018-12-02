/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;


import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.SearchBodyLocationTask;

import java.util.ArrayList;
import java.util.List;

public class SearchState {
    public SearchState() {}
    public void querySearchProblems(String id, String keyword, Callback cb) {
    }
    public void querySearchRecords(String id, String keyword, Callback cb) {
    }

    public static class SearchKeyword extends SearchState {
        public SearchKeyword() {}
    }

    public static class SearchGeoLocation extends SearchState {
        public SearchGeoLocation() {}

    }

    public static class SearchBodyLocation extends SearchState {
        public SearchBodyLocation() {}
        public void querySearchRecords(String id, String keyword, Callback cb) {
            new SearchBodyLocationTask(cb).execute(id, keyword);
        }
    }
}
