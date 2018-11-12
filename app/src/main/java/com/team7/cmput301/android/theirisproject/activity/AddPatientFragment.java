/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import com.team7.cmput301.android.theirisproject.R;

/**
 * AddPatientFragment is responsible for getting the Care Provider's input of an email, which it
 * then tells its controller to go search the database for that Patient and gives its hosting activity
 * the new list of Patients to display if updated.
 *
 * @author Jmmxp
 */
public class AddPatientFragment extends DialogFragment {

    private AddPatientDialogListener listener;

    public interface AddPatientDialogListener {
        void onFinishAddPatient(boolean success);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        AddPatientDialogListener parent = (AddPatientDialogListener) getActivity();

        builder.setTitle(R.string.add_patient_dialog_title)
                .setView(R.layout.dialog_add_patient)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Check if found that email, and use callback method for parent activity accordingly
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        return builder.create();
    }
}
