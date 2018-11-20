package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.DeleteProblemActivity;
import com.team7.cmput301.android.theirisproject.activity.ProblemListActivity;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.task.AddProblemTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

/**
 * Tests for EditPatientProfileActivity
 *
 * @author anticobalt
 */
public class DeleteProblemActivityTest extends ActivityInstrumentationTestCase2<DeleteProblemActivity> {

    int flag = 0;

    private Solo solo;
    private String title = "I hit my head";
    private String desc = "I have amnesia now";
    private String patientId = "pid";

    public DeleteProblemActivityTest() {
        super(DeleteProblemActivity.class);
    }

    @Override
    protected void setUp() {

        // make problem
        Problem problem = new Problem(title, desc, patientId);

        // make a callback that signals task completion
        Callback callback = new Callback(){
            @Override
            public void onComplete(Object res) {
                setFlag(1);
            }
        };

        // put patientID in Intent, as required for IrisActivity
        Intent intent = new Intent();
        intent.putExtra("patientId", patientId);
        setActivityIntent(intent);

        // add problem
        new AddProblemTask(callback).execute(problem);

        // start Solo
        solo = new Solo(getInstrumentation(), getActivity());

    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    public void testCorrectActivity() {
        String activityName = DeleteProblemActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity!", activityName);
    }

    public void testDeleteProblem() {

        // Wait for task to finish
        while (flag == 0){
            Timer.sleep(100);
        }
        flag = 0;

        // click delete button
        View deleteButton = solo.getView(R.id.do_delete_problem_button);
        solo.clickOnView(deleteButton);

        // confirm the text of the Problem is gone
        assertFalse(solo.searchText(title));
        assertFalse(solo.searchText(desc));

    }

    private void setFlag(int value) {
        flag = value;
    }

}
