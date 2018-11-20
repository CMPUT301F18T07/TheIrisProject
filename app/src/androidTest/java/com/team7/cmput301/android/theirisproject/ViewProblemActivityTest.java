package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.AddRecordActivity;
import com.team7.cmput301.android.theirisproject.activity.RecordListActivity;
import com.team7.cmput301.android.theirisproject.activity.ViewProblemActivity;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Comment;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.task.AddProblemTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.team7.cmput301.android.theirisproject.model.User.*;

/**
 * LoginActivityTest contains UI testing pertaining to the login screen
 *
 * @author jtwong
 * @see ViewProblemActivity
 */
public class ViewProblemActivityTest extends ActivityInstrumentationTestCase2<ViewProblemActivity> {

    private Solo solo;
    private String title = "Zombification";
    private String description = "I think I'm slowly turning into a zombie.";
    private String userid = "0";
    private RecordList records = new RecordList();
    private List<BodyPhoto> body_photos = new ArrayList<>();

    private String _id = "mememe";
    private String problemId = "mrmrmr";
    private String author = "John";
    private UserType role = UserType.CARE_PROVIDER;
    private Date date = new Date();
    private String body = "Zombies aren't real";

    private String blob = "blob";

    public ViewProblemActivityTest() {
        super(ViewProblemActivity.class);
    }

    @Override
    protected void setUp() {
        Problem problem = new Problem(title, description, userid, body_photos);
        problem.setRecords(records);
        problem.setID(_id);

        Intent intent = new Intent();
        intent.putExtra(ViewProblemActivity.EXTRA_PROBLEM_ID, _id);

        IrisProjectApplication.setCurrentUser(new Patient("testUser", "email@gmail.com", "1234567890"));

        setActivityIntent(intent);
        solo = new Solo(getInstrumentation(), getActivity());

        new AddProblemTask(new Callback<String>() {
            @Override
            public void onComplete(String res) {
                // Don't need to do anything wtih the result
            }
        }).execute(problem);
    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void testActivity() {
        String activityName = ViewProblemActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + activityName, activityName);
    }

    /**
     * Checking to see if the problem data is correctly displayed
     */
    @Test
    public void testCorrectProblemData() {
        Timer.sleep(1000);
        // Ensure that the problem data (title and description shows up)

        assertTrue(solo.searchText(title));
        assertTrue(solo.searchText(description));
    }

    /**
     * Checking to see if the view_record_button goes to RecordListActivity when pressed
     */
    @Test
    public void testViewRecord() {
        Button viewRecordButton = (Button) solo.getView(R.id.view_record_button);
        solo.clickOnView(viewRecordButton);
        String recordListActivityName = RecordListActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + recordListActivityName, recordListActivityName);
    }

    /**
     * Checking to see if the create_record_button goes to AddRecordActivity when pressed
     */
    @Test
    public void testCreateRecord() {
        Button createRecordButton = (Button) solo.getView(R.id.create_record_button);
        solo.clickOnView(createRecordButton);
        String addRecordActivityName = AddRecordActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + addRecordActivityName, addRecordActivityName);
    }

    /**
     * Checking to see if comments are correctly added
     */
    @Test
    public void testAddComment() {
        Button problemCommentSubmitButton = (Button) solo.getView(R.id.problem_comment_submit_button);
        EditText problemCommentBox = (EditText) solo.getView(R.id.problem_comment_box);

        solo.enterText(problemCommentBox, "This is a new comment");

        solo.clickOnView(problemCommentSubmitButton);
    }
}
