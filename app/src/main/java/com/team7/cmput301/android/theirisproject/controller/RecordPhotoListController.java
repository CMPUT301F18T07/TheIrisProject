package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.GetRecordPhotosTask;

import java.util.ArrayList;
import java.util.List;

/**
 * RecordPhotoListController's main purpose is to return a list of
 * all record photos associated with a problem
 *
 * @author jtfwong
 * */
public class RecordPhotoListController extends IrisController<List<RecordPhoto>> {

    private String problemId;

    @Override
    List<RecordPhoto> getModel(Bundle data) {
        return new ArrayList<>();
    }

    public RecordPhotoListController(Intent intent) {
        super(intent);
        problemId = intent.getStringExtra(Extras.EXTRA_PROBLEM_ID);
        model = getModel(intent.getExtras());
    }

    /**
     * queryRecordPhotos executes GetRecordPhotosTask and updates
     * the model in controller and also invoking the callback
     * given from activity
     * @param cb
     * */
    public void queryRecordPhotos(Callback cb) {
        new GetRecordPhotosTask(new Callback<List<RecordPhoto>>() {
            @Override
            public void onComplete(List<RecordPhoto> res) {
                model = res;
                cb.onComplete(model);
            }
        }).execute(problemId);
    }

    public List<RecordPhoto> getPhotos() { return model; }
}
