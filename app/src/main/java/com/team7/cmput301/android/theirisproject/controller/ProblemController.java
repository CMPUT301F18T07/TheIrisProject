package com.team7.cmput301.android.theirisproject.controller;

import android.content.Intent;
import android.os.Bundle;

import com.team7.cmput301.android.theirisproject.Callback;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.GetProblemTask;


public class ProblemController extends IrisController {
    String problemID;

    public ProblemController(Intent intent) {
        super(intent);
        this.problemID = intent.getExtras().getString("problem_id");
        this.model = getModel(intent.getExtras());
    }

    @Override
    Object getModel(Bundle data) {
        return new Problem();
    }

    public void getProblem(Callback cb) {
        new GetProblemTask(new Callback<Problem>() {
            @Override
            public void onComplete(Problem res) {
                model = res;
                cb.onComplete(res);
            }
        }).execute(problemID);
    }

}
