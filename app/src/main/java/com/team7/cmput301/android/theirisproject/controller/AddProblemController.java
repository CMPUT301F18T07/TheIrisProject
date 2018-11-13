/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.task.AddBodyPhotoTask;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.task.AddProblemTask;

import java.util.ArrayList;


/**
 * AddProblemController has methods to allow our AddProblemActivity
 * to interact with the database by POST requesting new problems to it
 *
 * @author itstc
 * */
public class AddProblemController extends IrisController<Problem> {

    private ArrayList<BodyPhoto> bodyPhotos = new ArrayList<BodyPhoto>();

    public AddProblemController(Intent intent) {
        super(intent);
    }

    /**
     * addBodyPhoto will scale the bitmap to 256x256 before adding it to
     * the imageList. It is required to be scaled before submitting to database
     *
     * @param img: our bitmap image
     * @return void
     * */
    public void addBodyPhoto(Bitmap img) {
        Bitmap res = Bitmap.createScaledBitmap(img, 256, 256, false);
        bodyPhotos.add(new BodyPhoto(IrisProjectApplication.getCurrentUser().getId(), res));
    }
    public ArrayList<BodyPhoto> getBodyPhotos() {
        return bodyPhotos;
    }

    /**
     * submitProblem is a method to asynchronously submit our problem
     * once we receive a response from the database, we return a callback
     * with a boolean result either successful or not
     *
     * @param title: Problem title
     * @param desc: Problem description
     * @param cb: callback method
     * @return void
     * */
    public void submitProblem(String title, String desc, Callback<Boolean> cb) {
        Problem submitProblem = new Problem(title, desc, IrisProjectApplication.getCurrentUser().getId());
        // add problem to our database
        new AddProblemTask(new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean result) {
                // Once we added the problem, add our body photo documents
                new AddBodyPhotoTask(new Callback<Boolean>() {
                    @Override
                    public void onComplete(Boolean res) {
                        cb.onComplete(result);
                    }
                }).execute(imageList);
            }
        }).execute(submitProblem);
    }

    @Override
    Problem getModel(Bundle data) {
        return null;
    }
}
