/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;
import android.util.Log;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Comment;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.model.Problem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Search;

import static com.team7.cmput301.android.theirisproject.IrisProjectApplication.INDEX;

/**
 * GetProblemTask asynchronously gets a problem from the database
 * first it queries the primitive fields of a problem. After the primitives
 * are collected, another query is executed for the body photos and comments
 * within a problem. Finally the callback given is executed with the problem state
 *
 * @author VinnyLuu
 */
public class GetProblemTask extends AsyncTask<String, Problem, Problem> {
    private Callback callback;
    private String problemIdQuery = "{\"query\": {\"term\": {\"problemId\": \"%s\"}}}";
    public GetProblemTask(Callback callback) {
        this.callback = callback;
    }
    /**
     * doInBackground will send a request to DB with desired problem's id
     * returning the problem
     * @param params use only params[0] containing the desired problem's id
     * @return Problem: Problem that corresponds to the problem id
     * */
    @Override
    protected Problem doInBackground(String... params) {
        Problem problemState = new Problem();
        try {
            // Start another thread to get comments and updating if done querying
            GetCommentTask comments = new GetCommentTask(new Callback<List<Comment>>() {
                @Override
                public void onComplete(List<Comment> res) {
                    Log.d("Iris", "Populating problems comments");
                    problemState.asyncSetComments(res);
                    publishProgress(problemState);
                }
            });
            comments.execute(params[0]);
            // populate primitive attributes in Problem
            Get get = new Get.Builder(INDEX,params[0])
                    .type("problem")
                    .build();
            JestResult res = IrisProjectApplication.getDB().execute(get);
            problemState.asyncCopyFields(res.getSourceAsObject(Problem.class));

            Log.d("Iris", "Populating problems fields");
            return problemState;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return problemState;
    }

    @Override
    protected void onProgressUpdate(Problem... values) {
        super.onProgressUpdate(values);
        callback.onComplete(values[0]);
    }

    @Override
    protected void onPostExecute(Problem res) {
        super.onPostExecute(res);
        callback.onComplete(res);
    }

    /**
     * GetCommentTask is a nested AsyncTask that will run
     * concurrently with GetProblemTask to speed up our process
     * in retrieving data for a problem.
     *
     * @author itstc
     * */
    public class GetCommentTask extends AsyncTask<String, Void, List<Comment>> {
        private Callback cb;
        public GetCommentTask(Callback cb) {
            this.cb = cb;
        }

        /**
         * doInBackground will query comments related to the
         * problem and return a list of comments
         *
         * @param params: params[0] should contain the problemId
         * @return List<Comment>
         * */
        @Override
        protected List<Comment> doInBackground(String... params) {
            List<Comment> commentsResult = new ArrayList<>();
            try {
                Search commentSearch = new Search.Builder(String.format(problemIdQuery, params[0]))
                        .addIndex(IrisProjectApplication.INDEX)
                        .addType("comment")
                        .build();
                commentsResult = IrisProjectApplication
                        .getDB()
                        .execute(commentSearch)
                        .getSourceAsObjectList(Comment.class, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return commentsResult;
        }

        @Override
        protected void onPostExecute(List<Comment> comments) {
            super.onPostExecute(comments);
            cb.onComplete(comments);
        }
    }

}
