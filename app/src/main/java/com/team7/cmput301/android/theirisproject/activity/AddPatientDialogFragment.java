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
import android.view.View;
import android.widget.EditText;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.AddPatientController;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.task.AddPatientTask;
import com.team7.cmput301.android.theirisproject.task.Callback;

/**
 * AddPatientDialogFragment is responsible for getting the Care Provider's input of an email, which it
 * then tells its controller to go search the database for that Patient and gives its hosting activity
 * the new list of Patients to display if updated.
 *
 * @author Jmmxp
 */
public class AddPatientDialogFragment extends DialogFragment {

    private AddPatientController controller;
    private AddPatientDialogListener listener;

    private EditText addPatientEditText;

    public interface AddPatientDialogListener {
        void onFinishAddPatient(boolean success);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        controller = new AddPatientController(getActivity().getIntent());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        listener = (AddPatientDialogListener) getActivity();

        View view = View.inflate(getActivity(), R.layout.dialog_add_patient, null);
        addPatientEditText = view.findViewById(R.id.add_patient_edit_text);

        builder.setTitle(R.string.add_patient_dialog_title)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Check if found that email, and use callback method for parent activity accordingly
                        Callback<Boolean> callback = new Callback<Boolean>() {
                            @Override
                            public void onComplete(Boolean success) {
                                listener.onFinishAddPatient(success);
                            }
                        };
                        controller.addPatient(addPatientEditText.getText().toString(),
                                (CareProvider) IrisProjectApplication.getCurrentUser(),
                                callback);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        return builder.create();
    }
}
