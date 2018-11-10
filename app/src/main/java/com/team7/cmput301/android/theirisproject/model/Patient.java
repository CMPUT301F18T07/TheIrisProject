/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private List<CareProvider> careProviders = new ArrayList<>();
    private ProblemList problems;

    public List<CareProvider> getCareProviders() {
        return this.careProviders;
    }

    public ProblemList getProblems() {
        return this.problems;
    }

    public Patient(String name, String email, String phoneNumber) {
        super(UserType.PATIENT, name, email, phoneNumber);
    }
}
