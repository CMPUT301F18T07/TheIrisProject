/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import java.util.ArrayList;
import java.util.List;

public class ProblemList {
    private ArrayList<Problem> problems = new ArrayList<Problem>();

    public ArrayList<Problem> getProblems() {
        return this.problems;
    }

    public ProblemList() {

    }
    public ProblemList(List<Problem> problems) {
        this.problems = (ArrayList<Problem>) problems;
    }

    public void add(Problem problem) {

    }

    public void delete(Problem problem) {

    }

    public List<Problem> asList() { return this.problems; }

    public Problem getProblem(String id) {
        for(Problem problem: this.problems) {
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

}
