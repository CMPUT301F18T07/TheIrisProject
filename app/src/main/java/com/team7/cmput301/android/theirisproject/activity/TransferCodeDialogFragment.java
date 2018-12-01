/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.AddPatientController;
import com.team7.cmput301.android.theirisproject.task.Callback;

public class TransferCodeDialogFragment extends DialogFragment {

    private static final String ARG_CODE = "code";

    private TextView codeTextView;

    public static TransferCodeDialogFragment newInstance(String code) {
        
        Bundle args = new Bundle();
        args.putString(ARG_CODE, code);

        TransferCodeDialogFragment fragment = new TransferCodeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String code = getArguments().getString(ARG_CODE, "");

        View view = View.inflate(getActivity(), R.layout.dialog_show_code, null);
        codeTextView = view.findViewById(R.id.code_text_view);
        codeTextView.setText(code);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.transfer_code_dialog_title)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Exit when clicked
                        dismiss();
                    }
                })
                .create();

        return builder.create();
    }

}
