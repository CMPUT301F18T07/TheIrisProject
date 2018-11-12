package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.controller.AddProblemController;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.AddProblemTask;

/**
 * AddProblemActivity is a form to add an activity,
 * by click on submit we are sending a Problem object
 * to our database to be stored (handle offline by storing it in local json).
 *
 * @author itstc
 * */
public class AddProblemActivity extends IrisActivity {
    private AddProblemController controller;
    private TextView name;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_addproblem);

        controller = createController(getIntent());
        name = findViewById(R.id.problem_title_edit_text);
        desc = findViewById(R.id.problem_description_edit_text);

        // set click listener to submit button
        findViewById(R.id.problem_submit_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // send new problem to database, callback has result true if successful, else false
                controller.submitProblem(name.getText().toString(), desc.getText().toString(), new Callback<Boolean>() {
                    @Override
                    public void onComplete(Boolean success) {
                        if(success) {
                            // end Activity returning to ProblemListActivity
                            Toast.makeText(AddProblemActivity.this, "New Problem Created!", Toast.LENGTH_LONG).show();
                            finish();
                        }else {
                            Toast.makeText(AddProblemActivity.this, "Uh oh something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


    @Override
    protected AddProblemController createController(Intent intent) {
        return new AddProblemController(intent);
    }
}
