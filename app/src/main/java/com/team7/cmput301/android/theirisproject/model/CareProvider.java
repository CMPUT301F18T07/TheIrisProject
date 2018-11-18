/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a CareProvider-type User
 *
 * @author jtfwong
 */
public class CareProvider extends User {

    transient private List<Patient> patients = new ArrayList<>();
    private List<String> patientIds = new ArrayList<>();

    /* Constructors */

    public CareProvider(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber, UserType.CARE_PROVIDER);
    }

    /* Basic setters + adders */

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void addPatientId(String patientId) {
        patientIds.add(patientId);
    }

    /* Basic getters */

    public List<Patient> getPatients() {
        return patients;
    }

    public List<String> getPatientIds() {
        return patientIds;
    }

    /* Searches */

    public ProblemList getPatientProblems(Patient patient) {
        return null;
    }

    public Problem getPatientProblemById(String problemId) {
        return null;
    }

}