/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import java.util.ArrayList;

public class CareProvider extends User {
    private ArrayList<Patient> patients = new ArrayList<Patient>();

    public ArrayList<Patient> getPatients() {
        return this.patients;
    }

    public ProblemList getPatientProblems(Patient patient) {
        return null;
    }

    public void CareProvider(String name, String email, String phoneNumber) {

    }
}
