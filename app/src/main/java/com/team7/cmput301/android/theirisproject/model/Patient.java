/*
 * Copyright (c) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;

public class Patient extends User {
    private ArrayList<CareProvider> careProviders = new ArrayList<CareProvider>();
    private ProblemList problems;

    public ArrayList<CareProvider> getCareProviders() {
        return this.careProviders;
    }

    public ProblemList getProblems() {
        return this.problems;
    }

    public void Patient(String name, String email, String phoneNumber) {

    }
}
