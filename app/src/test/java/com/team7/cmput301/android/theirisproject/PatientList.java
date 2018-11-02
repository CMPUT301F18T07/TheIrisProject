package com.team7.cmput301.android.theirisproject;

import org.junit.Assert;
import org.junit.Test;

public class PatientList {
    @Test
    public void testAddPatient() {
        PatientList patientList = new PatientList();
        Patient patient = new Patient("TestPatient", "TestEmail@hotmail.com", "123-456-789");

        Assert.assertEquals(patientList.getPatients().size(), 0);
        patientList.addPatient(patient);

        Assert.assertEquals(patientList.getPatients().size(), 1);
    }

    @Test
    public void testRemovePatient() {
        PatientList patientList = new PatientList();
        Patient patient = new Patient("TestPatient", "TestEmail@hotmail.com", "123-456-789");

        patientList.addPatient(patient);
        Assert.assertEquals(patientList.getPatients().size(), 1);

        patientList.removePatient(patient);
        Assert.assertEquals(patientList.getPatients().size(), 0);
    }
}
