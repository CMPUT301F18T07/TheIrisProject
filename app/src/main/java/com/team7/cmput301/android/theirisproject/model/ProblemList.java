/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of Problems pertaining a particular Patient
 * Besides doing typical List things like add/delete, handles more advanced
 * actions like self-lookup.
 * 1-1 association with Patient.
 *
 * @author itstc
 * @author Jmmxp
 */
public class ProblemList implements Iterable<Problem> {

    private ArrayList<Problem> problems = new ArrayList<>();

    /* Constructors */

    public ProblemList(List<Problem> problems) {
        this.problems = (ArrayList<Problem>) problems;
    }

    public ProblemList() {
    }

    /* Basic getters */

    public ArrayList<Problem> getProblems() {
        return problems;
    }

    /* Basic list operations */

    public void add(Problem problem) {
        problems.add(problem);
    }

    public void remove(Problem problem) {
        problems.remove(problem);
    }

    public boolean contains(Problem problem) {
        return problems.contains(problem);
    }

    public int length() {
        return problems.size();
    }

    /* Searches */

    public Problem getProblemById(String id) {
        for(Problem problem: problems) {
            if (problem.getId().equals(id)) return problem;
        }
        return null;
    }

    /* Misc */

    @NonNull
    @Override
    public Iterator<Problem> iterator() {
        return problems.iterator();
    }

}