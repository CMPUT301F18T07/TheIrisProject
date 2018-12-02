/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.team7.cmput301.android.theirisproject.IrisProjectApplication;
import com.team7.cmput301.android.theirisproject.PatientListRecyclerAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Contact;
import com.team7.cmput301.android.theirisproject.model.Patient;
import com.team7.cmput301.android.theirisproject.task.AddPatientTask;
import com.team7.cmput301.android.theirisproject.task.Callback;
import com.team7.cmput301.android.theirisproject.task.GetPatientListByContactTask;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.*;

/**
 * ContactsFragment is a Fragment that displays a list of Contacts that the logged-in
 * Care Provider has in their Contacts app, all the Contacts of which are registered Patients
 * in Iris.
 *
 * Resources used for this class:
 * https://developer.android.com/training/contacts-provider/retrieve-names
 * https://developer.android.com/training/contacts-provider/retrieve-details
 * https://stackoverflow.com/questions/33720856/how-to-fetch-only-phone-number-contacts-from-android-device/51911818#51911818, User Collins A
 * https://gist.github.com/srayhunter/47ab2816b01f0b00b79150150feb2eb2, User srayhunter
 * as a reference
 */
public class ContactsFragment extends Fragment {

    private RecyclerView patientsView;
    private PatientListRecyclerAdapter adapter;

    private final String[] PROJECTION_CONTACT = {
            Contacts._ID,
            Contacts.DISPLAY_NAME
    };

    private final String[] PROJECTION_PHONE = {
            CommonDataKinds.Phone.NUMBER
    };

    private final String[] PROJECTION_EMAIL = {
            CommonDataKinds.Email.ADDRESS
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        patientsView = view.findViewById(R.id.contacts_recycler_view);
        patientsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        patientsView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        adapter = new PatientListRecyclerAdapter(new ArrayList<>());
        patientsView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact_list_action_save_contacts: {
                addSelectedContacts();
                getActivity().finish();
                return true;
            }
            default: {
                return false;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        runGetPatientListTask();
    }

    /**
     * Find out which Patients have been checked to be imported and call AddPatientTask for all of them
     */
    private void addSelectedContacts() {
        List<Patient> patients = adapter.getPatients();
        List<Patient> selectedPatients = new ArrayList<>();

        for (int i = 0; i < patients.size(); i++) {
            PatientListRecyclerAdapter.ViewHolder viewHolder = (PatientListRecyclerAdapter.ViewHolder) patientsView.findViewHolderForAdapterPosition(i);
            if (viewHolder.isChecked()) {
                selectedPatients.add(patients.get(i));
            }
        }

        CareProvider careProvider = (CareProvider) IrisProjectApplication.getCurrentUser();
        for (Patient patient : selectedPatients) {
            // Note that this is duplicated in AddPatientTask!
            careProvider.addPatient(patient);

            new AddPatientTask(new Callback<Boolean>() {
                @Override
                public void onComplete(Boolean res) {
                    // do nothing on complete
                }
            }).execute(patient.getAddCode(), true);
        }
    }

    /**
     * Get List of Patients by checking Contacts and render them to the RecyclerView
     */
    private void runGetPatientListTask() {
        List<Contact> contacts = getContactList();

        // Search each of these contacts in database, get a List of Patients from the search
        new GetPatientListByContactTask(new Callback<List<Patient>>() {
            @Override
            public void onComplete(List<Patient> res) {
                if (res != null) {
                    adapter.setPatients(res);
                    render();
                }
            }
        }).execute(contacts.toArray(new Contact[contacts.size()]));
    }

    private void render() {
        adapter.notifyDataSetChanged();
    }

    /**
     * Gets the List of Contacts for the current user
     * Only contains Contacts that have either a phone number or email address
     * @return
     */
    private List<Contact> getContactList() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        List<Contact> contacts = new ArrayList<>();

        Cursor cursor = contentResolver.query(Contacts.CONTENT_URI, PROJECTION_CONTACT,null,null, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));

                String phone = null;
                String email = null;

                // Get the phone number of the contact
                Cursor phones = contentResolver.query(CommonDataKinds.Phone.CONTENT_URI, PROJECTION_PHONE,
                        CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] {id},null);
                if (phones != null && phones.moveToFirst()) {
                    phone = phones.getString(phones.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                }
                phones.close();

                // Get the email of the contact
                Cursor emails = contentResolver.query(CommonDataKinds.Email.CONTENT_URI, PROJECTION_EMAIL,
                        CommonDataKinds.Email.CONTACT_ID + " = ?", new String[] {id},null);
                if (emails != null && emails.moveToFirst()) {
                    email = emails.getString(emails.getColumnIndex(CommonDataKinds.Email.ADDRESS));
                }
                emails.close();

                // Add a new contact
                if (phone != null || email != null) {
                    contacts.add(new Contact(name, phone, email));
                }
            } while (cursor.moveToNext());

        }
        cursor.close();

        return contacts;
    }

}

