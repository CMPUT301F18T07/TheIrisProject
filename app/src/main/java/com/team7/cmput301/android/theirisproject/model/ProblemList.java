/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProblemList implements Iterable<Problem> {
    private ArrayList<Problem> problems = new ArrayList<Problem>();

    public ProblemList(List<Problem> problems) {
        this.problems = (ArrayList<Problem>) problems;
    }

    public ProblemList() {
    }

    public ArrayList<Problem> getProblems() {
        return problems;
    }

    public void delete(Problem problem) {

    }

    public List<Problem> asList() { return problems; }

    public Problem getProblemByID(String id) {
        for(Problem problem: problems) {
            if(problem.getId() == id) return problem;
        }
        return null;
    }

    public boolean contains(Problem problem) {
        return false;
    }

    public int length() {
        return 0;
    }

    @NonNull
    @Override
    public Iterator<Problem> iterator() {
        return problems.iterator();
    }
}
