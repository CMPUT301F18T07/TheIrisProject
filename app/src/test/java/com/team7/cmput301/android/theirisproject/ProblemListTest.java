package com.team7.cmput301.android.theirisproject;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProblemListTest {

    private List<Problem> problems = new ArrayList<>();
    private Problem p1 = new Problem("Major Life Threatening Issue 54", "Pls help me", new RecordList(), new ArrayList<BodyPhoto>());
    private Problem p2 = new Problem("Something not that bad", "My head hurts sometimes", new RecordList(), new ArrayList<BodyPhoto>());

    @Test
    public void testAdd() {

        ProblemList p_list = new ProblemList();
        p_list.add(p1);
        p_list.add(p2);

        Assert.assertTrue(p_list.contains(p1));
        Assert.assertTrue(p_list.contains(p2));

    }

    @Test
    public void testDelete() {

        ProblemList p_list = new ProblemList();
        p_list.add(p1);
        p_list.add(p2);
        p_list.delete(p1);
        p_list.delete(p2);

        Assert.assertFalse(p_list.contains(p1));
        Assert.assertFalse(p_list.contains(p2));

    }

    @Test
    public void testLength() {

        ProblemList p_list = new ProblemList();
        List<Problem> test_list = new ArrayList<>(Arrays.asList(p1, p2));

        Assert.assertEquals(test_list.size(), p_list.length());

    }

}
