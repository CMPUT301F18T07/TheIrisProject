package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.Profile;

import org.junit.Assert;
import org.junit.Test;

public class ProfileTest {
    @Test
    public void testProfile() {
        String name = "UserOne";
        String email = "UserOne@hotmail.com";
        String phoneNumber = "123-456-789";

        Profile profile = new Profile(name, email, phoneNumber);

        Assert.assertEquals(name, profile.getUsername());
        Assert.assertEquals(email, profile.getEmail());
        Assert.assertEquals(phoneNumber, profile.getPhoneNumber());
    }

    @Test
    public void testUpdateProfile() {
        String name = "UserOne";
        String email = "UserOne@hotmail.com";
        String phoneNumber = "123-456-789";

        Profile profile = new Profile(name, email, phoneNumber);

        String updatedName = "UserTwo";
        String updatedEmail = "UserOne@hotmail.com";
        String updatedPhoneNumber = "234-567-890";
        profile.updateProfile(updatedName, updatedEmail, updatedPhoneNumber);

        Assert.assertEquals(updatedName, profile.getUsername());
        Assert.assertEquals(updatedEmail, profile.getEmail());
        Assert.assertEquals(updatedPhoneNumber, profile.getPhoneNumber());
    }
}
