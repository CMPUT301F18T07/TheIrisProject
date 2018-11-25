/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.task.AddPatientTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

import java.util.List;

public class PatientListRecyclerAdapter extends RecyclerView.Adapter<PatientListRecyclerAdapter.ViewHolder> {
    private List<Patient> patients;

    public PatientListRecyclerAdapter(List<Patient> patients) {
        this.patients = patients;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView username;
        private TextView email;
        private TextView phone;
        private CheckBox add;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            username = itemView.findViewById(R.id.contact_item_username);
            email = itemView.findViewById(R.id.contact_item_email);
            phone = itemView.findViewById(R.id.contact_item_phone);
            add = itemView.findViewById(R.id.contact_item_add);
        }

        public void bind(Patient patient) {
            username.setText(patient.getUsername());
            email.setText(patient.getEmail());
            phone.setText(patient.getPhone());
        }

        public void addContact() {
            new AddPatientTask(new Callback<Boolean>() {
                @Override
                public void onComplete(Boolean res) {

                }
            }).execute(username.getText().toString());
        }

        public boolean isChecked() {
            return add.isChecked();
        }

        @Override
        public void onClick(View view) {
            add.setChecked(!add.isChecked());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact_item, parent, false);

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

    public List<Patient> getPatients() {
        return patients;
    }
}


