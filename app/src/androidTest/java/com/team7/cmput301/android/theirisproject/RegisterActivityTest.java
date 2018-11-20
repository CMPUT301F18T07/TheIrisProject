package com.team7.cmput301.android.theirisproject;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.activity.RegisterActivity;
import com.team7.cmput301.android.theirisproject.helper.DeleteUserTask;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.User;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.RegisterTask;

import org.junit.Test;

/**
 * Contains register tests for RegisterActivity
 *
 *
 * @author Jmmxp
 * @see RegisterActivity
 */
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

    // We have to decide if we want to use Robotium or Espresso

    private Solo solo;

    public RegisterActivityTest() {
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
    @Test
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
    @Test
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

        User user = new Patient(nameEditText.getText().toString(), emailEditText.getText().toString(),
                phoneEditText.getText().toString());

        new RegisterTask(new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean registerSuccess) {
                if (!registerSuccess) {
                    assert false;
                }
            }
        }).execute(user);

        solo.clickOnView(registerButton);

        assertTrue(solo.waitForText(getString(R.string.register_failure)));
    }

    /**
     * Test registration when all fields are filled out and user is registered successfully
     * Currently only works if the account is guaranteed not to be registered
     */
    @Test
    public void testRegisterSuccess() {
        Button registerButton = (Button) solo.getView(R.id.register_button);

        EditText nameEditText = (EditText) solo.getView(R.id.name_edit_text);
        EditText passwordEditText = (EditText) solo.getView(R.id.password_edit_text);
        EditText emailEditText = (EditText) solo.getView(R.id.email_edit_text);
        EditText phoneEditText = (EditText) solo.getView(R.id.phone_edit_text);

        String testEmail = "johndoe@gmail.com";

        solo.enterText(nameEditText, "John Doe");
        solo.enterText(passwordEditText, "badpassword");
        solo.enterText(emailEditText, testEmail);
        solo.enterText(phoneEditText, "123-456-7890");

        new DeleteUserTask(new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean deleteSuccess) {
                if (!deleteSuccess) {
                    assert false;
                }
            }
        }).execute(testEmail);

        // Must pause to properly give time to delete the user if it exists
        // Need to determine if this is always going to be sufficient time
        Timer.sleep(1000);

        solo.clickOnView(registerButton);

        assertTrue(solo.waitForText(getString(R.string.register_success)));
    }

    private String getString(int stringId) {
        return getActivity().getString(stringId);
    }

}
