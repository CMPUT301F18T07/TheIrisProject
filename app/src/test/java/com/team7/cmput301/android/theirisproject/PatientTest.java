package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.RecordList;
import com.team7.cmput301.android.theirisproject.model.User.UserType;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PatientTest {

    String name = "PatientOne";
    String email = "PatientOne@hotmail.com";
    String phoneNumber = "123-456-789";
    private UserType type = UserType.PATIENT;

    private String title;
    private String description;
    private String userid;
    private RecordList records;
    private List<BodyPhoto> body_photos;

    @Test
    public void testPatient() {
        // Test create patient

        Patient patient = getTestPatient();

        Assert.assertEquals(name, patient.getUsername());
        Assert.assertEquals(email, patient.getEmail());
        Assert.assertEquals(phoneNumber, patient.getPhone());
        Assert.assertEquals(type, patient.getType());
    }

    @Test
    public void testAddCareProvider() {
        // Test adding a care provider

        Patient patient = getTestPatient();
        CareProvider careProvider = getTestCareProvider();

        patient.addCareProvider(careProvider);
        Assert.assertEquals(patient.getCareProviders().size(), 1);
        Assert.assertEquals(patient.getCareProviders().get(0), careProvider);
    }

    @Test
    public void testAddProblem() {
        // Test adding a problem

        Patient patient = getTestPatient();
        Problem problem = getTestProblem();

        patient.addProblem(problem);
        Assert.assertEquals(patient.getProblems().contains(problem), true);
    }

    private Patient getTestPatient() {

        String name = "PatientOne";
        String email = "PatientOne@hotmail.com";
        String phoneNumber = "123-456-789";
        UserType type = UserType.PATIENT;

        return new Patient(name, email, phoneNumber);

    }

    private CareProvider getTestCareProvider() {

        String name = "CareProviderOne";
        String email = "CareProviderOne@hotmail.com";
        String phoneNumber = "123-456-789";
        UserType type = UserType.CARE_PROVIDER;

        return new CareProvider(name, email, phoneNumber);

    }

    private Problem getTestProblem() {

        String title = "Zombification";
        String description = "I think I'm slowly turning into a zombie.";
        String userid = "0";
        RecordList records = new RecordList();

        return new Problem(title, description, userid);
    }
}
