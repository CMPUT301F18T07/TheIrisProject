package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.AddProblemController;
import com.team7.cmput301.android.theirisproject.task.Callback;

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
        setContentView(R.layout.activity_add_problem);

        controller = createController(getIntent());
        name = findViewById(R.id.problem_title_edit_text);
        desc = findViewById(R.id.problem_description_edit_text);

        // set click listener to submit button
        findViewById(R.id.problem_submit_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // send new problem to database, callback has result true if successful, else false
                controller.submitProblem(name.getText().toString(), desc.getText().toString(), new Callback<String>() {
                    @Override
                    public void onComplete(String id) {
                        if(id != null) {
                            // end Activity returning to ProblemListActivity
                            Intent intent = new Intent(AddProblemActivity.this, ViewProblemActivity.class);
                            intent.putExtra(ViewProblemActivity.EXTRA_PROBLEM_ID, id);
                            Toast.makeText(AddProblemActivity.this, "New Problem Created!", Toast.LENGTH_LONG).show();
                            startActivity(intent);
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
