package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;

public class ViewCareproviderProfileActivity extends IrisActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careprovider_view_profile);
        ViewCareproviderProfileActivity.this.setTitle("@string/Viewprofile_title");
    }


    @Override
    protected IrisController createController(Intent intent) {
        return null;
    }
}
