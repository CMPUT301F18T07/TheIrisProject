/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Patient-type User
 *
 * @author itstc
 * @author Jmmxp
 * @author anticobalt
 */
public class Patient extends User {

    private List<CareProvider> careProviders = new ArrayList<>();
    private ProblemList problems = new ProblemList();

    /* Constructors */

    public Patient(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber, UserType.PATIENT);
    }

    /* Basic getters */

    public List<CareProvider> getCareProviders() {
        return careProviders;
    }

    public ProblemList getProblems() {
        return problems;
    }

    /* Basic list operations */

    public void addProblem(Problem problem){
        problems.add(problem);
    }

    /* Searches */

    public Problem getProblemById(String id) {
        return problems.getProblemById(id);
    }

    public Record getRecordById(String id) {
        for (Problem problem: problems) {
            Record record = problem.getRecords().getRecordById(id);
            if (record != null) {
                return record;
            }
        }
        return null;
    }

}