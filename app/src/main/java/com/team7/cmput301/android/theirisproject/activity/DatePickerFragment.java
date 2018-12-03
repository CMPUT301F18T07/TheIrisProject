/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

/**
 * Allows for date editing.
 * A copy of the picker fragment used in https://github.com/anticobalt/asl2-FeelsBook
 *
 * @author anticobalt
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditProblemActivity parent;
    private Integer year;
    private Integer month;
    private Integer day;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new DatePickerDialog(getActivity(), this, this.year, this.month, this.day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Save changes immediately
        this.parent.setDateInts(year, month, dayOfMonth);
    }

    public void setDefaults(EditProblemActivity parent, Integer year, Integer month, Integer day){
        /* Sets the initial calender value, and reference to parent to allow for saving
         * */
        this.parent = parent;
        this.year = year;
        this.month = month;
        this.day = day;
    }

}
