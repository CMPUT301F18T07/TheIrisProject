/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.UserListAdapter;
import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.IrisController;
import com.team7.cmput301.android.theirisproject.model.Patient;

/**
 * ViewProfileActivity is for allowing the patient to view their profile information.
 * @author caboteja
 */


public class ViewProfileActivity extends IrisActivity {

    private Patient patient;

    private TextView name;
    private TextView email;
    private TextView phone;
    private ListView careProviders;

    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_profile);

        patient = (Patient) IrisProjectApplication.getCurrentUser();

        name = findViewById(R.id.view_profile_name_text_view);
        email = findViewById(R.id.view_profile_email_text_view);
        phone = findViewById(R.id.view_profile_phone_text_view);
        careProviders = findViewById(R.id.view_profile_care_provider_list_view);

        name.setText(patient.getName());
        email.setText(patient.getEmail());
        phone.setText(patient.getPhone());


        adapter = new UserListAdapter(this, R.layout.list_care_provider_item, patient.getCareProviders());

        careProviders.setAdapter(adapter);
    }

    @Override
    protected IrisController createController(Intent intent) {
        return null;
    }
}
