package com.team7.cmput301.android.theirisproject;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.activity.RegisterActivity;

/**
 * Contains register tests for RegisterActivity
 *
 *
 * @author Jmmxp
 * @see RegisterActivity
 */
public class RegisterActivityTest2 extends ActivityInstrumentationTestCase2<RegisterActivity> {

    // We have to decide if we want to use Robotium or Espresso

    private Solo solo;

    public RegisterActivityTest2() {
        super(RegisterActivity.class);
    }

    @Override
    protected void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    public void testActivity() {
        String activityName = RegisterActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + activityName, activityName);
    }

    /**
     * Test registration when not all fields have been filled out
     */
    public void testRegisterIncomplete() {
        Button registerButton = (Button) solo.getView(R.id.register_button);

        solo.clickOnView(registerButton);

        assertTrue(solo.waitForText(getString(R.string.register_incomplete)));

        // Try registering with only the name field filled out
        EditText nameEditText = (EditText) solo.getView(R.id.name_edit_text);
        solo.enterText(nameEditText, "John Doe");

        solo.clickOnView(registerButton);

        assertTrue(solo.waitForText(getString(R.string.register_incomplete)));
    }

    /**
     * Test registration when all fields are filled out but the email is already in use
     * Currently only works if the account has already been registered
     */
    public void testRegisterAlreadyRegistered() {
        Button registerButton = (Button) solo.getView(R.id.register_button);

        EditText nameEditText = (EditText) solo.getView(R.id.name_edit_text);
        EditText passwordEditText = (EditText) solo.getView(R.id.password_edit_text);
        EditText emailEditText = (EditText) solo.getView(R.id.email_edit_text);
        EditText phoneEditText = (EditText) solo.getView(R.id.phone_edit_text);

        solo.enterText(nameEditText, "John Doe");
        solo.enterText(passwordEditText, "badpassword");
        solo.enterText(emailEditText, "johndoe@gmail.com");
        solo.enterText(phoneEditText, "123-456-7890");

        solo.clickOnView(registerButton);

        // TODO: Add user to DB before doing this test
        assertTrue(solo.waitForText(getString(R.string.register_failure)));
    }

    /**
     * Test registration when all fields are filled out and user is registered successfully
     * Currently only works if the account is guaranteed not to be registered
     */
    public void testRegisterSuccess() {
        Button registerButton = (Button) solo.getView(R.id.register_button);

        EditText nameEditText = (EditText) solo.getView(R.id.name_edit_text);
        EditText passwordEditText = (EditText) solo.getView(R.id.password_edit_text);
        EditText emailEditText = (EditText) solo.getView(R.id.email_edit_text);
        EditText phoneEditText = (EditText) solo.getView(R.id.phone_edit_text);

        solo.enterText(nameEditText, "John Doe");
        solo.enterText(passwordEditText, "badpassword");
        solo.enterText(emailEditText, "johndoe@gmail.com");
        solo.enterText(phoneEditText, "123-456-7890");

        solo.clickOnView(registerButton);

        // TODO: Delete user from DB if exists before doing this test
        // assertTrue(solo.waitForText(getString(R.string.register_success)));
    }

    private String getString(int stringId) {
        return getActivity().getString(stringId);
    }

}
