/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.model.Contact;

/**
 * ContactDialogFragment is a dialog to show
 * the contact information of a user
 *
 * @author itstc
 * */
public class ContactDialogFragment extends DialogFragment {
    private static final String CONTACT_DATA = "data";

    public static ContactDialogFragment newInstance(Contact contact) {
        ContactDialogFragment f = new ContactDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(CONTACT_DATA, contact);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialog = inflater.inflate(R.layout.fragment_contact_card, container, false);
        Bundle extras = getArguments();
        TextView nameText = dialog.findViewById(R.id.fragment_contact_name);
        TextView emailText = dialog.findViewById(R.id.fragment_contact_email);
        TextView phoneText = dialog.findViewById(R.id.fragment_contact_phone);

        Contact contact = extras.getParcelable(CONTACT_DATA);
        nameText.setText(contact.getName());
        emailText.setText(contact.getEmail());
        phoneText.setText(contact.getPhone());

        return dialog;
    }

}
