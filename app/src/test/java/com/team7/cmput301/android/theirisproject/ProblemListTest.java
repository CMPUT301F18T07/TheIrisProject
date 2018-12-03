package com.team7.cmput301.android.theirisproject;

import com.team7.cmput301.android.theirisproject.model.Problem;
import com.team7.cmput301.android.theirisproject.model.ProblemList;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProblemListTest {

    private List<Problem> problems = new ArrayList<>();
    private Problem p1 = new Problem("Major Life Threatening Issue 54", "Pls help me", "0");
    private Problem p2 = new Problem("Something not that bad", "My head hurts sometimes", "0");
    private Problem p3 = new Problem("Noticed new rash", "Gotta keep track", "0");

    @Test
    public void testContains() {
        // Test if problem list contains a problem

        ProblemList pList = new ProblemList();
        pList.add(p1);
        pList.add(p2);

        Assert.assertTrue(pList.contains(p1));
        Assert.assertTrue(pList.contains(p2));
        Assert.assertFalse(pList.contains(p3));

    }

    @Test
    public void testAdd() {
        // Test adding a problem to problem list

        ProblemList pList = new ProblemList();
        pList.add(p1);
        pList.add(p2);

        Assert.assertTrue(pList.contains(p1));
        Assert.assertTrue(pList.contains(p2));

    }

    @Test
    public void testDelete() {
        // Test deleting a problem from problem list

        ProblemList pList = new ProblemList();
        pList.add(p1);
        pList.add(p2);

        Assert.assertTrue(pList.contains(p1));
        Assert.assertTrue(pList.contains(p2));

        pList.remove(p1);
        pList.remove(p2);

        Assert.assertFalse(pList.contains(p1));
        Assert.assertFalse(pList.contains(p2));

    }

    @Test
    public void testLength() {
        // Test getting length of problem list

        ProblemList pList = new ProblemList();
        List<Problem> testList = new ArrayList<>(Arrays.asList(p1, p2));

        Assert.assertEquals(testList.size(), pList.length());

    }

}
