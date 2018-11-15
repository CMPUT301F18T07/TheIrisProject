/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;

/**
 * EditCareproviderProfileActivity is for allowing the care provider to edit their profile information.
 * @author caboteja
 */

public class EditCareproviderProfileActivity extends IrisActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careprovider_edit_profile);
        EditCareproviderProfileActivity.this.setTitle("@string/cEditprofile_title_text");
    }

    @Override
    protected IrisController createController(Intent intent) {
        return null;
    }
}
