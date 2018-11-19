package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.PatientList;

import org.junit.Assert;
import org.junit.Test;

public class PatientListTest {
    @Test
    public void testAddPatient() {
        PatientList patientList = new PatientList();
        Patient patient = new Patient("TestPatient", "TestEmail@hotmail.com", "123-456-789");

        Assert.assertEquals(patientList.getPatients().size(), 0);
        patientList.addPatient(patient);

        Assert.assertEquals(patientList.getPatients().size(), 1);
        Assert.assertEquals(patientList.getPatients(), patient);

    }

    @Test
    public void testRemovePatient() {
        PatientList patientList = new PatientList();
        Patient patient = new Patient("TestPatient", "TestEmail@hotmail.com", "123-456-789");

        patientList.addPatient(patient);
        Assert.assertEquals(patientList.getPatients().size(), 1);
        Assert.assertEquals(patientList.getPatients(), patient);

        patientList.removePatient(patient);
        Assert.assertEquals(patientList.getPatients().size(), 0);
    }
}
