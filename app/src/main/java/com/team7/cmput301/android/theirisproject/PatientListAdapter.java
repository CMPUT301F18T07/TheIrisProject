/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.model.Patient;

import java.util.List;

public class PatientListAdapter extends ArrayAdapter<Patient> {

    private Activity context;
    private int resource;
    private List<Patient> patients;


    public PatientListAdapter(@NonNull Activity context, int resource, @NonNull List<Patient> patients) {
        super(context, resource, patients);
        this.context = context;
        this.resource = resource;
        this.patients = patients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View patientView = inflater.inflate(resource, parent, false);

        TextView name = patientView.findViewById(R.id.patient_item_username);
        TextView email = patientView.findViewById(R.id.patient_item_email);

        Patient patient = patients.get(position);

        name.setText(patient.getUsername());
        email.setText(patient.getEmail());

        return patientView;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

}
