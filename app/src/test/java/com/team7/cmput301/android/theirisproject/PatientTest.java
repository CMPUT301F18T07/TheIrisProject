package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.Patient;

import org.junit.Assert;
import org.junit.Test;

public class PatientTest {
    @Test
    public void testPatient() {
        String name = "PatientOne";
        String email = "PatientOne@hotmail.com";
        String phoneNumber = "123-456-789";
        String role = "Patient";

        Patient patient = new Patient(name, email, phoneNumber);

        Assert.assertEquals(name, patient.getContact().getUsername());
        Assert.assertEquals(email, patient.getContact().getEmail());
        Assert.assertEquals(phoneNumber, patient.getContact().getPhoneNumber());
        Assert.assertEquals(role, patient.getRole());
    }

}
