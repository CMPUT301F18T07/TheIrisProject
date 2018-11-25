/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.User;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the User class
 *
 * @author Jmmxp
 */
public class UserTest {
    private User user = new Patient("UserOne", "UserOne@hotmail.com", "123-456-789");

    @Test
    public void testUser() {
        String name = "UserOne";
        String email = "UserOne@hotmail.com";
        String phoneNumber = "123-456-789";

        Assert.assertEquals(name, user.getUsername());
        Assert.assertEquals(email, user.getEmail());
        Assert.assertEquals(phoneNumber, user.getPhone());
    }

    @Test
    public void testEditContact() {
        String updatedName = "UserTwo";
        String updatedEmail = "UserOne@hotmail.com";
        String updatedPhoneNumber = "234-567-890";
        user.updateProfile(updatedEmail, updatedPhoneNumber);

        Assert.assertEquals(updatedName, user.getUsername());
        Assert.assertEquals(updatedEmail, user.getEmail());
        Assert.assertEquals(updatedPhoneNumber, user.getPhone());
    }
}
