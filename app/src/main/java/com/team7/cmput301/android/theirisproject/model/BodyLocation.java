/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

/**
 * A specific point on a Body Photo.
 * Associated with a Record.
 *
 * @see Record
 * @author Jmmxp
 */
public class BodyLocation {

    private int x;
    private int y;
    private BodyPhoto src;

    public BodyLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /* Basic getters */

    public BodyPhoto getSrc() {
        return null;
    }

    /* Advanced setters */

    public void update(int x, int y) {

    }

    /* Misc */

    public int[] asInt() {
        return new int[0];
    }

}