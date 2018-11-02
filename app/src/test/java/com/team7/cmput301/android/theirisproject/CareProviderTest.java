package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.CareProvider;

import org.junit.Assert;
import org.junit.Test;

public class CareProviderTest {
    @Test
    public void testCareProvider() {
        // Test creation of a Care Provider
        String name = "CareProviderOne";
        String email = "CareProviderOne@hotmail.com";
        String phoneNumber = "123-456-789";
        String role = "CareProvider";

        CareProvider careProvider = new CareProvider(name, email, phoneNumber);

        Assert.assertEquals(name, careProvider.getContact().getUsername());
        Assert.assertEquals(email, careProvider.getContact().getEmail());
        Assert.assertEquals(phoneNumber, careProvider.getContact().getPhoneNumber());
        Assert.assertEquals(role, careProvider.getRole());
    }

}
