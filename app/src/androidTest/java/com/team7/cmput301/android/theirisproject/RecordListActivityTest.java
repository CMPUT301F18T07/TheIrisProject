package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.DeleteProblemActivity;
import com.team7.cmput301.android.theirisproject.activity.RecordListActivity;
import com.team7.cmput301.android.theirisproject.activity.ViewRecordActivity;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.task.AddRecordTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

/**
 * Tests for RecordListActivity
 *
 * @author anticobalt
 */
public class RecordListActivityTest extends ActivityInstrumentationTestCase2<RecordListActivity> {

    int flag = 0;

    private Solo solo;
    private String user = "user";
    private String title = "My computer is talking to me";
    private String desc = "Only in the mornings tho";
    private String problemId = "mid";

    public RecordListActivityTest() {
        super(RecordListActivity.class);
    }

    @Override
    protected void setUp() {

        // make record to ensure at least one item will show in activity
        Record record = new Record(user, problemId, title, desc);

        // make a callback that signals task completion
        Callback callback = new Callback(){
            @Override
            public void onComplete(Object res) {
                setFlag(1);
            }
        };

        // put problemId in Intent, as required for IrisActivity
        Intent intent = new Intent();
        intent.putExtra("problemId", problemId);
        setActivityIntent(intent);

        // add problem
        new AddRecordTask(callback).execute(record);

        // start Solo
        solo = new Solo(getInstrumentation(), getActivity());

    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    public void testCorrectActivity() {
        String activityName = RecordListActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity!", activityName);
    }

    public void testViewRecordList() {

        // Wait for task to finish
        while (flag == 0){
            Timer.sleep(100);
        }
        flag = 0;

        // check that title shows up
        assertTrue(solo.searchText(title));

        // click first list item
        solo.clickInList(0);

        // confirm the activity closes and next one opens
        solo.waitForActivity(ViewRecordActivity.class);

    }

    private void setFlag(int value) {
        flag = value;
    }

}
