package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.User.UserType;

import org.junit.Assert;
import org.junit.Test;

public class PatientTest {
    @Test
    public void testPatient() {
        String name = "PatientOne";
        String email = "PatientOne@hotmail.com";
        String phoneNumber = "123-456-789";
        UserType type = UserType.PATIENT;

        Patient patient = new Patient(name, email, phoneNumber);

        Assert.assertEquals(name, patient.getName());
        Assert.assertEquals(email, patient.getEmail());
        Assert.assertEquals(phoneNumber, patient.getPhoneNumber());
        Assert.assertEquals(type, patient.getType());
    }

}
