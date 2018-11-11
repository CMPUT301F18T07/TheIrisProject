package com.team7.cmput301.android.theirisproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.AddProblemTask;

public class AddProblemActivity extends AppCompatActivity {

    TextView name;
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_addproblem);

        name = findViewById(R.id.problem_title_edit_text);
        desc = findViewById(R.id.problem_description_edit_text);

        // set click listener to submit button
        findViewById(R.id.problem_record_problem_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // send new problem to database, returning true if successful, else false
                Problem submitProblem = new Problem(
                        name.getText().toString(),
                        desc.getText().toString(),
                        getIntent().getExtras().getString("user"));

                new AddProblemTask(new Callback<Boolean>() {
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
                }).execute(submitProblem);
            }
        });
    }
}
