/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a CareProvider-type User
 *
 * @author Jmmxp
 */
public class CareProvider extends User {

    private List<Patient> patients = new ArrayList<>();

    /* Constructors */

    public CareProvider(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber, UserType.CARE_PROVIDER);
    }

    /* Basic getters */

    public List<Patient> getPatients() {
        return patients;
    }

    /* Searches */

    public ProblemList getPatientProblems(Patient patient) {
        return null;
    }

}