/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;
import java.util.List;

public class CareProvider extends User {
    private List<Patient> patients = new ArrayList<>();

    public List<Patient> getPatients() {
        return patients;
    }

    public ProblemList getPatientProblems(Patient patient) {
        return null;
    }

    public CareProvider(String name, String email, String phoneNumber) {
        super("CareProvider", name, email, phoneNumber);
    }
}
