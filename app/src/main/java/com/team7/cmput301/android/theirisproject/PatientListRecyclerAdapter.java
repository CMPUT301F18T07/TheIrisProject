/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.model.Patient;

import java.util.List;

public class PatientListRecyclerAdapter extends RecyclerView.Adapter<PatientListRecyclerAdapter.ViewHolder> {
    private List<Patient> patients;

    public PatientListRecyclerAdapter(List<Patient> patients) {
        this.patients = patients;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView email;
        private TextView phone;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.patient_item_username);
            email = itemView.findViewById(R.id.patient_item_email);
            phone = itemView.findViewById(R.id.patient_item_phone);
        }

        public void bind(Patient patient) {
            username.setText(patient.getUsername());
            email.setText(patient.getEmail());
            phone.setText(patient.getPhone());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_patient_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(patients.get(position));
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

}


