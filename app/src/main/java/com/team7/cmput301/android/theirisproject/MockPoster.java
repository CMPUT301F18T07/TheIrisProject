/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import java.io.IOException;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * To more easily add models to Elastic Search
 *
 * @author anticobalt
 */
public class MockPoster {

    String type;

    /**
     * @param type user, record, or problem; the index
     */
    public MockPoster(String type){
        this.type = type;
    }

    /**
     * @param obj The object to be saved in Elastic Search (e.g. a Record, Problem)
     */
    public void post(Object obj){

        Boolean result = false;

        try {
            Index post = new Index.Builder(obj).index(IrisProjectApplication.INDEX).type(type).build();
            DocumentResult res = IrisProjectApplication.getDB().execute(post);
            result = res.isSucceeded();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (result) {
                System.out.println("Success!");
            } else {
                System.out.println("Failed!");
            }
        }

    }

}
