package com.team7.cmput301.android.theirisproject;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.team7.cmput301.android.theirisproject.activity.RegisterActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * RegisterActivityTest2 contains tests pertaining to registering users in RegisterActivity
 *
 * @author Jmmxp
 * @see RegisterActivity
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterActivityTest {

    private Activity activity;

    @Rule
    public ActivityTestRule<RegisterActivity> activityRule = new ActivityTestRule<>(RegisterActivity.class);

    @Before
    public void setUp() {
        activity = activityRule.getActivity();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Test registration when not all fields have been filled out
    @Test
    public void testRegisterIncomplete() {
        onView(withId(R.id.register_button))
                .perform(click());

        checkToast(getString(R.string.register_incomplete));

        fillEditText(R.id.name_edit_text, "John Doe");


        closeKeyboardAndWait();
        onView(withId(R.id.register_button))
                .perform(click());

        checkToast(getString(R.string.register_incomplete));
    }

    // Currently only works if the account has already been registered

    /**
     * Test registration when all fields are filled out but the email is already in use
     */
    @Test
    public void testRegisterAlreadyRegistered() {
        fillEditText(R.id.name_edit_text, "John Doe");
        fillEditText(R.id.password_edit_text, "badpassword");
        fillEditText(R.id.email_edit_text, "johndoe@gmail.com");
        fillEditText(R.id.phone_edit_text, "123-456-7890");

        closeKeyboardAndWait();
        onView(withId(R.id.register_button))
                .perform(click());

        checkToast(getString(R.string.register_failure));
    }

    /**
     * Test registration when all fields are filled out and user is registered successfully
     */
    @Test
    public void testRegisterSuccess() {
        fillEditText(R.id.name_edit_text, "John Doe");
        fillEditText(R.id.password_edit_text, "badpassword");
        fillEditText(R.id.email_edit_text, "johndoe2@gmail.com");
        fillEditText(R.id.phone_edit_text, "123-456-7890");

        closeKeyboardAndWait();
        onView(withId(R.id.register_button))
                .perform(click());

        // TODO: Delete test user from the DB, or use a mock DB before doing this test
        // checkToast(getString(R.string.register_success));
    }

    private void fillEditText(int editTextResId, String text) {
        onView(withId(editTextResId))
                .perform(typeText(text));
    }

    private void closeKeyboardAndWait() {
        Espresso.closeSoftKeyboard();
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Check if a Toast displays given text
    // REFERENCE: https://stackoverflow.com/questions/28390574/checking-toast-message-in-android-espresso
    private void checkToast(String toastText) {
        onView(withText(toastText))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    private String getString(int stringResId) {
        return activity.getString(stringResId);
    }

}
