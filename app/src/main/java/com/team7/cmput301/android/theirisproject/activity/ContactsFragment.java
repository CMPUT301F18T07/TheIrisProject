/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.team7.cmput301.android.theirisproject.R;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.*;

/**
 * ContactsFragment is a Fragment that displays a list of Contacts that the currently logged in
 * Care Provider has in their Contacts app, all the Contacts of which are registered Patients
 * in the Iris application
 *
 * Resources used for this class:
 * https://developer.android.com/training/contacts-provider/retrieve-names
 * https://developer.android.com/training/contacts-provider/retrieve-details
 * https://stackoverflow.com/questions/33720856/how-to-fetch-only-phone-number-contacts-from-android-device/51911818#51911818, User Collins A
 * https://gist.github.com/srayhunter/47ab2816b01f0b00b79150150feb2eb2, User srayhunter
 * as a reference
 */
public class ContactsFragment extends Fragment {

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

    // A UI Fragment must inflate its View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        System.out.println("total contacts size: " + cursor.getCount());
        cursor.close();

        for (Contact contact : contacts) {
            System.out.println(contact.getName() + ", " + contact.getPhone() + ", " + contact.getEmail());
        }

    }

}

class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
