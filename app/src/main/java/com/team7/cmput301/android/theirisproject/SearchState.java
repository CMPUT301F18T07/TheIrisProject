/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;


import com.team7.cmput301.android.theirisproject.helper.StringHelper;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.SearchBodyLocationTask;
import com.team7.cmput301.android.theirisproject.task.SearchGeoLocationTask;
import com.team7.cmput301.android.theirisproject.task.SearchKeywordTask;
import com.team7.cmput301.android.theirisproject.task.SearchProblemsByRecordsTask;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchState are the different states that we can search by.
 *
 * @author itstc
 * */
public class SearchState {
    public SearchState() {}
    public void querySearchProblems(String id, String keyword, List<Problem> problems, List<Record> records, Callback cb) {
    }

    public void querySearchRecords(String id, String keyword, List<Problem> problems, List<Record> records, Callback cb) {
    }

    public Callback<List<Problem>> updateProblemCallback(List<Problem> problems, Callback cb) {
        return new Callback<List<Problem>>() {
            @Override
            public void onComplete(List<Problem> res) {
                problems.addAll(res);
                cb.onComplete(res);
            }
        };
    }

    public Callback<List<Record>> updateRecordCallback(List<Record> records, Callback cb) {
        return new Callback<List<Record>>() {
            @Override
            public void onComplete(List<Record> res) {
                records.addAll(res);
                cb.onComplete(res);
            }
        };
    }

    public static class SearchKeyword extends SearchState {
        public SearchKeyword() {}

        @Override
        public void querySearchProblems(String id, String keyword, List<Problem> problems, List<Record> records, Callback cb) {
            new SearchKeywordTask<Problem>(updateProblemCallback(problems, cb), "problem", Problem.class).execute(id, keyword);
        }

        @Override
        public void querySearchRecords(String id, String keyword, List<Problem> problems, List<Record> records, Callback cb) {
            new SearchKeywordTask<Record>(updateRecordCallback(records, cb), "record", Record.class).execute(id, keyword);
        }
    }

    public static class SearchGeoLocation extends SearchState {
        public SearchGeoLocation() {}

        @Override
        public void querySearchProblems(String id, String keyword, List<Problem> problems, List<Record> records, Callback cb) {
        }

        @Override
        public void querySearchRecords(String id, String keyword, List<Problem> problems, List<Record> records, Callback cb) {
            String trim = keyword.trim();
            String[] split = trim.split(",");
            System.out.println(split[0]);
            System.out.println(split[1]);

            new SearchGeoLocationTask(new Callback<List<Record>>() {
                @Override
                public void onComplete(List<Record> res) {
                    records.addAll(res);
                    new SearchProblemsByRecordsTask(new Callback<List<Problem>>() {
                        @Override
                        public void onComplete(List<Problem> result) {
                            problems.addAll(result);
                            cb.onComplete(res);
                        }
                    }).execute(res);
                }
            }).execute(id, split[0], split[1]);
        }
    }

    public static class SearchBodyLocation extends SearchState {
        public SearchBodyLocation() {}

        @Override
        public void querySearchProblems(String id, String keyword, List<Problem> problems, List<Record> records, Callback cb) {
        }

        public void querySearchRecords(String id, String keyword, List<Problem> problems, List<Record> records, Callback cb) {
            new SearchBodyLocationTask(new Callback<List<Record>>() {
                @Override
                public void onComplete(List<Record> res) {
                    records.addAll(res);
                    new SearchProblemsByRecordsTask(new Callback<List<Problem>>() {
                        @Override
                        public void onComplete(List<Problem> result) {
                            problems.addAll(result);
                            cb.onComplete(res);
                        }
                    }).execute(res);
                }
            }).execute(id, keyword);
        }
    }
}
