package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.CareProvider;

import org.junit.Assert;
import org.junit.Test;

import static com.team7.cmput301.android.theirisproject.model.User.*;

public class CareProviderTest {
    @Test
    public void testCareProvider() {
        // Test creation of a Care Provider
        String name = "CareProviderOne";
        String email = "CareProviderOne@hotmail.com";
        String phoneNumber = "123-456-789";
        UserType type = UserType.CARE_PROVIDER;

        CareProvider careProvider = new CareProvider(name, email, phoneNumber);

        Assert.assertEquals(name, careProvider.getUsername());
        Assert.assertEquals(email, careProvider.getEmail());
        Assert.assertEquals(phoneNumber, careProvider.getPhoneNumber());
        Assert.assertEquals(type, careProvider.getType());
    }

}
