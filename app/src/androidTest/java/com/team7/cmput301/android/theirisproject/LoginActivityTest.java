package com.team7.cmput301.android.theirisproject;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.LoginActivity;
import com.team7.cmput301.android.theirisproject.activity.ProblemListActivity;
import com.team7.cmput301.android.theirisproject.activity.RegisterActivity;

/**
 * LoginActivityTest contains UI testing pertaining to the login screen
 *
 * @author Jmmxp
 * @see LoginActivity
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public LoginActivityTest() {
        super(LoginActivity.class);
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
        String activityName = LoginActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + activityName, activityName);
    }

    /**
     * Logging in with an empty email
     */
    public void testLoginFailureEmptyEmail() {
        setupLogin("");

        solo.waitForText(getActivity().getString(R.string.login_failure));
    }

    /**
     * Logging in with an email that doesn't exist
     */
    public void testLoginFailure() {
        setupLogin("foobar@gmail.com");

        solo.waitForText(getActivity().getString(R.string.login_failure));
    }

    /**
     * Logging in with an e-mail that does exist
     */
    public void testLoginSuccess() {
        setupLogin("johndoe@gmail.com");

        solo.waitForText(getActivity().getString(R.string.login_success));

        // TODO: Code below assumes the user is a Patient, there should be a dedicated login test for both a Patient and CP.
        // Need to implement simplified start-of-test reading and deleting from test DB if possible

//        String problemListActivityName = ProblemListActivity.class.getSimpleName();
//        solo.assertCurrentActivity("Wrong activity found, should be " + problemListActivityName, problemListActivityName);
    }

    /**
     * Helper function to help set-up the login screen with the given email and click on the Login button
     * @param email
     */
    private void setupLogin(String email) {
        Button loginButton = (Button) solo.getView(R.id.login_button);
        EditText emailEditText = (EditText) solo.getView(R.id.login_email_field);

        solo.enterText(emailEditText, email);
        solo.clickOnView(loginButton);
    }

}
