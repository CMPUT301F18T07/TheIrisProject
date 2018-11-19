package com.team7.cmput301.android.theirisproject;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.LoginActivity;
import com.team7.cmput301.android.theirisproject.activity.PatientListActivity;
import com.team7.cmput301.android.theirisproject.activity.ProblemListActivity;
import com.team7.cmput301.android.theirisproject.helper.DeleteUserTask;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.RegisterTask;

import org.junit.Test;

/**
 * LoginActivityTest contains UI testing pertaining to the login screen
 *
 * @author Jmmxp
 * @see LoginActivity
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    private String testName = "John Doe";
    private String testEmail = "johndoe@gmail.com";
    private String testPhone = "123-456-7890";

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

    @Test
    public void testActivity() {
        String activityName = LoginActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + activityName, activityName);
    }

    /**
     * Logging in with an empty email
     */
    @Test
    public void testLoginFailureEmptyEmail() {
        setupLogin("");

        solo.waitForText(getActivity().getString(R.string.login_failure));
    }

    /**
     * Logging in with an email that doesn't exist
     */
    @Test
    public void testLoginFailure() {
        deleteUserWithEmail(testEmail);

        setupLogin(testEmail);

        Timer.sleep(1000);

        solo.waitForText(getActivity().getString(R.string.login_failure));
    }

    /**
     * Logging in with an e-mail that does exist, where the User is of type Patient
     */
    @Test
    public void testLoginSuccessPatient() {
        deleteUserWithEmail(testEmail);

        new RegisterTask(new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean registerResult) {
                if (!registerResult) {
                    assert false;
                }
            }
        }).execute(getTestPatient());

        setupLogin(testEmail);

        Timer.sleep(1000);

        solo.waitForText(getActivity().getString(R.string.login_success));

        String problemListActivityName = ProblemListActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + problemListActivityName, problemListActivityName);
    }

    /**
     * Logging in with an e-mail that does exist, where the User is of type Care Provider
     */
    @Test
    public void testLoginSuccessCareProvider() {
        deleteUserWithEmail(testEmail);

        new RegisterTask(new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean registerResult) {
                if (!registerResult) {
                    assert false;
                }
            }
        }).execute(getTestCareProvider());

        setupLogin(testEmail);

        Timer.sleep(1000);

        solo.waitForText(getActivity().getString(R.string.login_success));

        String patientListActivityName = PatientListActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + patientListActivityName, patientListActivityName);
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

    private void deleteUserWithEmail(String email) {
        new DeleteUserTask(new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean res) {
                // Don't need to do anything with result
            }
        }).execute(email);

        Timer.sleep(1000);
    }

    private Patient getTestPatient() {
        return new Patient(testName, testEmail, testPhone);
    }

    private CareProvider getTestCareProvider() {
        return new CareProvider(testName, testEmail, testPhone);
    }

}
