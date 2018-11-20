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
public class GetProblemTask extends AsyncTask<String, Void, Problem> {

    private Callback callback;
    private String problemIdQuery = "{\"query\": {\"term\": {\"problemId\": \"%s\"}}}";
    public GetProblemTask(Callback callback) {
        this.callback = callback;
    }

    /**
     * doInBackground will send a request to DB with desired problem's id
     * returning the problem
     * @param params: use only params[0] containing the desired problem's id
     * @return Problem: Problem that corresponds to the problem id
     * */
    @Override
    protected Problem doInBackground(String... params) {

        Problem problemState = null;
        try {
            // populate primitive attributes in Problem
            Get get = new Get.Builder(INDEX,params[0])
                    .type("problem")
                    .build();
            JestResult res = IrisProjectApplication.getDB().execute(get);
            problemState = res.getSourceAsObject(Problem.class);

            // populate the bodyPhotos attribute in Problem
            problemState.setBodyPhotos(getBodyPhotosFromProblemId(params[0]));
            // populate the comments attribute in Problem
            problemState.setComments(getCommentsFromProblemId(params[0]));

            return problemState;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return problemState;
    }

    /**
     * getBodyPhotosFromProblemId will query bodyphotos related to the
     * problem and return a list of bodyphotos with the bitmap image
     *
     * @param id
     * @return List<BodyPhoto>
     * */
    private List<BodyPhoto> getBodyPhotosFromProblemId(String id) {
        List<BodyPhoto> bodyPhotosResult = new ArrayList<>();
        try {
            Search bodyPhotoSearch = new Search.Builder(String.format(problemIdQuery, id))
                    .addIndex(IrisProjectApplication.INDEX)
                    .addType("bodyphoto")
                    .build();
            bodyPhotosResult = IrisProjectApplication
                    .getDB()
                    .execute(bodyPhotoSearch)
                    .getSourceAsObjectList(BodyPhoto.class, true);
            for(BodyPhoto bp: bodyPhotosResult) bp.convertBlobToPhoto();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bodyPhotosResult;
    }
    /**
     * getCommentsFromProblemId will query comments related to the
     * problem and return a list of comments
     *
     * @param id
     * @return List<Comment>
     * */
    private List<Comment> getCommentsFromProblemId(String id) {
        List<Comment> commentsResult = new ArrayList<>();
        try {
            Search commentSearch = new Search.Builder(String.format(problemIdQuery, id))
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
    protected void onPostExecute(Problem res) {
        super.onPostExecute(res);
        callback.onComplete(res);
    }
}
