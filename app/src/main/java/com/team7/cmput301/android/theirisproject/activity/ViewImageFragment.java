/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.helper.DateHelper;

import java.util.Date;

/**
 * ViewImageFragment is a dialog to show an enlarged
 * photo of the one pressed in ImageListAdapter
 *
 * @author itstc
 * */
public class ViewImageFragment extends DialogFragment {
    private String metadata;
    private Bitmap photo;
    private String date;
    private TextView metaDataView;
    private ImageView photoView;
    private TextView dateView;

    public ViewImageFragment() {}

    public static ViewImageFragment newInstance(String metadata, Bitmap photo, Date date) {
        Bundle args = new Bundle();
        args.putString("metadata", metadata);
        args.putParcelable("photo", photo);
        args.putString("date", DateHelper.format(date));
        ViewImageFragment fragment = new ViewImageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        metadata = getArguments().getString("metadata");
        photo = (Bitmap)getArguments().getParcelable("photo");
        date = getArguments().getString("date");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        metaDataView = view.findViewById(R.id.fragment_image_meta);
        photoView =  view.findViewById(R.id.fragment_image_photo);
        dateView = view.findViewById(R.id.fragment_image_date);

        metaDataView.setText(metadata);
        photoView.setImageBitmap(photo);
        dateView.setText(date);
    }
}
