/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.task;

import android.os.AsyncTask;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Comment;

import java.io.IOException;

import io.searchbox.core.Index;

/**
 * AddCommentTask adds a comment asynchronously on another thread
 * and will return the result status: success or error
 * */
public class AddCommentTask extends AsyncTask<Comment, Void, Boolean> {
    Callback cb;
    public AddCommentTask(Callback cb) {
        this.cb = cb;
    }
    @Override
    protected Boolean doInBackground(Comment... params) {
        try {
            Index addComment = new Index.Builder(params[0])
                    .index(IrisProjectApplication.INDEX)
                    .type("comment")
                    .build();
            Boolean res = IrisProjectApplication.getDB().execute(addComment).isSucceeded();
            Thread.sleep(5000);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean res) {
        super.onPostExecute(res);
        cb.onComplete(res);
    }
}
