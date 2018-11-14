package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;

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
