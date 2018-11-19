package com.team7.cmput301.android.theirisproject.helper;

/**
 * Helper class for tests that provides methods relating to sleeping and timing
 *
 * @author Jmmxp
 */
public class Timer {
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
