package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.PatientList;

import org.junit.Assert;
import org.junit.Test;

public class PatientListTest {
    @Test
    public void testAddPatient() {
        // Test adding patient to patient list

        PatientList patientList = new PatientList();
        Patient patient = new Patient("TestPatient", "TestEmail@hotmail.com", "123-456-789");

        Assert.assertEquals(patientList.getPatients().size(), 0);
        patientList.getPatients().add(patient);

        Assert.assertEquals(patientList.getPatients().size(), 1);
        Assert.assertEquals(patientList.getPatients().get(0), patient);
    }

    @Test
    public void testRemovePatient() {
        // Test removing patient from patient list

        PatientList patientList = new PatientList();
        Patient patient = new Patient("TestPatient", "TestEmail@hotmail.com", "123-456-789");

        patientList.getPatients().add(patient);
        Assert.assertEquals(patientList.getPatients().size(), 1);
        Assert.assertEquals(patientList.getPatients().get(0), patient);

        patientList.getPatients().remove(patient);
        Assert.assertEquals(patientList.getPatients().size(), 0);
    }
}
