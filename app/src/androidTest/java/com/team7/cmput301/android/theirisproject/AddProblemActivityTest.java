package com.team7.cmput301.android.theirisproject;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.AddProblemActivity;
import com.team7.cmput301.android.theirisproject.activity.RegisterActivity;
import com.team7.cmput301.android.theirisproject.model.Patient;

/**
 * AddProblemActivityTest contains automated UI testing pertaining to adding a new problem
 */
public class AddProblemActivityTest extends ActivityInstrumentationTestCase2<AddProblemActivity> {

    private Solo solo;

    public AddProblemActivityTest() {
        super(AddProblemActivity.class);
    }

    @Override
    protected void setUp() {
        // Make sure to instantiate Solo after setting the User for the app,
        // otherwise we get NPE from currentUser.getId() in AddProblemController
        Patient patient = new Patient("John Doe", "johndoe@gmail.com", "123-456-7890");
        patient.setId("testId");
        IrisProjectApplication.setCurrentUser(patient);

        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    public void testActivity() {
        String activityName = AddProblemActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + activityName, activityName);
    }

    /**
     * Add a problem and check for success (currently, there is nothing that guarantees failure)
     */
    public void testAddProblem() {
        Button addButton = (Button) solo.getView(R.id.problem_submit_button);
        EditText titleEditText = (EditText) solo.getView(R.id.problem_title_edit_text);
        EditText descEditText = (EditText) solo.getView(R.id.problem_description_edit_text);

        solo.enterText(titleEditText, "My wrist hurts");
        solo.enterText(descEditText, "My wrist hurts whenever I start programming!");

        solo.clickOnView(addButton);

        solo.waitForText(getActivity().getString(R.string.add_problem_success));
    }

}
