/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;
import java.util.List;

public class CareProvider extends User {
    transient private List<Patient> patients = new ArrayList<>();
    private List<String> patientIds = new ArrayList<>();

    public List<Patient> getPatients() {
        return patients;
    }

    public ProblemList getPatientProblems(Patient patient) {
        return null;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void addPatientId(String patientId) {
        patientIds.add(patientId);
    }

    public List<String> getPatientIds() {
        return patientIds;
    }

    /**
     * Generates a String representing the concatenation of all the patientIds with a comma in between
     * separating all of them e.g. id1, id2, id3
     *
     * Used to concatenate a new Patient ID into existing ones for a Care Provider
     */


    public CareProvider(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber, UserType.CARE_PROVIDER);
    }
}