package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;

import org.junit.Assert;
import org.junit.Test;

import static com.team7.cmput301.android.theirisproject.model.User.*;

public class CareProviderTest {

    private String name;
    private String email;
    private String phoneNumber;
    private UserType type;

    @Test
    public void testCareProvider() {
        // Test creation of a Care Provider

        CareProvider careProvider = getTestCareProvider();

        Assert.assertEquals(name, careProvider.getName());
        Assert.assertEquals(email, careProvider.getEmail());
        Assert.assertEquals(phoneNumber, careProvider.getPhone());
        Assert.assertEquals(type, careProvider.getType());
    }

    @Test
    public void testAddPatient() {

        CareProvider careProvider = getTestCareProvider();
        Patient patient = getTestPatient();

        Assert.assertEquals(careProvider.getPatients().size(), 0);
        careProvider.addPatient(patient);
        Assert.assertEquals(careProvider.getPatients().size(), 1);
        Assert.assertEquals(careProvider.getPatients().get(0), patient);

    }

    private CareProvider getTestCareProvider() {

        String name = "CareProviderOne";
        String email = "CareProviderOne@hotmail.com";
        String phoneNumber = "123-456-789";
        UserType type = UserType.CARE_PROVIDER;

        return new CareProvider(name, email, phoneNumber);

    }

    private Patient getTestPatient() {

        String name = "PatientOne";
        String email = "PatientOne@hotmail.com";
        String phoneNumber = "123-456-789";
        UserType type = UserType.PATIENT;

        return new Patient(name, email, phoneNumber);

    }

}
