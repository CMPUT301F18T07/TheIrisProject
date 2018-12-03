/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.User;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.fail;

/**
 * Tests for the User class
 *
 * @author Jmmxp
 */
public class UserTest {
    private User user = new Patient("UserOne", "UserOne@hotmail.com", "123-456-789");

    @Test
    public void testUser() {
        // Test creating user

        String name = "UserOne";
        String email = "UserOne@hotmail.com";
        String phoneNumber = "123-456-789";

        Assert.assertEquals(name, user.getUsername());
        Assert.assertEquals(email, user.getEmail());
        Assert.assertEquals(phoneNumber, user.getPhone());
    }

    @Test
    public void testEditContact() {
        // Test editing user

        String updatedName = "UserTwo";
        String updatedEmail = "UserOne@hotmail.com";
        String updatedPhoneNumber = "234-567-890";
        user.updateProfile(updatedEmail, updatedPhoneNumber);

        Assert.assertEquals(updatedName, user.getUsername());
        Assert.assertEquals(updatedEmail, user.getEmail());
        Assert.assertEquals(updatedPhoneNumber, user.getPhone());
    }

    @Test
    public void testIncorrectNameUsed() {
        // Test catch exception of existing username

        try {
            String name1 = "UserOne";
            String email1 = "UserRealOne@hotmail.com";
            String phoneNumber1 = "123-456-788";

            String name2 = "UserOne";
            String email2 = "UserOne@hotmail.com";
            String phoneNumber2 = "123-456-789";

            User user1 = new Patient(name1, email1, phoneNumber1);
            User user2 = new Patient(name2, email2, phoneNumber2);

            fail("Should throw an exception if username is already in use");
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void testIncorrectNameShortLength() {
        // Test catch exception of username too short

        try {
            String name = "hi";
            String email = "UserOne@hotmail.com";
            String phoneNumber = "123-456-789";

            User user = new Patient(name, email, phoneNumber);

            fail("Should throw an exception if username length is less than 8 characters");
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void testIncorrectNameLongLength() {
        // Test catch exception of username too long

        try {
            String name = "ihaveareallyreallyreallyreallyreallyreallylongname";
            String email = "UserOne@hotmail.com";
            String phoneNumber = "123-456-789";

            User user = new Patient(name, email, phoneNumber);

            fail("Should throw an exception if username length is more than 20 characters");
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void testIncorrectNameSpecial() {
        // Test catch exception of username containing special characters
        
        try {
            String name = "你好(*&#@!(asf";
            String email = "UserOne@hotmail.com";
            String phoneNumber = "123-456-789";

            User user = new Patient(name, email, phoneNumber);

            fail("Should throw an exception if username contains special characters");
        } catch (Exception e) {
            assert(true);
        }
    }
}
