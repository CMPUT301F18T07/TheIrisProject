package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;

public class EditPatientProfileActivity extends IrisActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);
        EditPatientProfileActivity.this.setTitle("@string/pEditprofile_title_text");
    }

    @Override
    protected IrisController createController(Intent intent) {
        return null;
    }
}
