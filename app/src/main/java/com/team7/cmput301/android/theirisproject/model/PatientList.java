/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;

/**
 * A collection of Patients associated with a Care Provider.
 * 1-1 association with Care Provider.
 *
 * @see CareProvider
 * @author jtfwong
 */
public class PatientList {

    private ArrayList<Patient> patients = new ArrayList<Patient>();

    /* Basic getters */

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    /* Basic list operations */

    public void addPatient(Patient patient) {

    }

    public void removePatient(Patient patient) {

    }

}