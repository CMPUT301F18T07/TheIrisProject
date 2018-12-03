/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.model;

import io.searchbox.annotations.JestId;

/**
 * TransferCode is used to save and retrieve transfer codes from the Elasticsearch database
 *
 * @author Jmmxp
 */
public class TransferCode {

    @JestId
    private String _id;

    private String code;
    private String username;

    public TransferCode(String code, String username) {
        this.code = code;
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return _id;
    }
}
