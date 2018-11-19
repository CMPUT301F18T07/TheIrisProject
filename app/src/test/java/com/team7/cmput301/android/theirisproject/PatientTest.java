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

    private String name;
    private String email;
    private String phoneNumber;
    private UserType type;

    private String title;
    private String description;
    private String userid;
    private RecordList records;
    private List<BodyPhoto> body_photos;

    @Test
    public void testPatient() {
        Patient patient = getTestPatient();

        Assert.assertEquals(name, patient.getName());
        Assert.assertEquals(email, patient.getEmail());
        Assert.assertEquals(phoneNumber, patient.getPhone());
        Assert.assertEquals(type, patient.getType());
    }

    @Test
    public void testAddCareProvider() {

        Patient patient = getTestPatient();
        CareProvider careProvider = getTestCareProvider();

        patient.addCareProvider(careProvider);
        Assert.assertEquals(patient.getCareProviders().size(), 1);
    }

    @Test
    public void testAddProblem() {

        Patient patient = getTestPatient();
        Problem problem = getTestProblem();

        patient.addProblem(problem);
        Assert.assertEquals(patient.getProblems(), problem);
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
        List<BodyPhoto> body_photos = new ArrayList<>();

        return new Problem(title, description, userid, body_photos);
    }
}
