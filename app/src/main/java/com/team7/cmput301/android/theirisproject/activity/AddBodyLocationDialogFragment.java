/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.ImageConverter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.helper.DateHelper;
import com.team7.cmput301.android.theirisproject.model.BodyLocation;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

/**
 * AddBodyLocationDialogFragment creates a dialog to click on
 * selected bodyphoto to set a point on the image. Used to create
 * a bodylocation for a record
 *
 * @see com.team7.cmput301.android.theirisproject.model.Record
 * @author itstc
 * */
public class AddBodyLocationDialogFragment extends DialogFragment {

    private BodyPhoto bodyPhotoSrc;
    private ImageView bodyPhotoView;
    private ImageView bodyLocationCanvas;
    private Button saveBodyLocationButton;

    private Bitmap bodyPhotoBitmap;
    private Canvas canvas;
    private Paint paint;
    private boolean dirty;

    private AddBodyLocationDialogListener listener;
    private BodyLocation location;

    public interface AddBodyLocationDialogListener {
        void onFinishAddBodyLocation(BodyLocation location, Bitmap image);
    }


    public AddBodyLocationDialogFragment() {}

    public static AddBodyLocationDialogFragment newInstance(BodyPhoto photo) {
        Bundle args = new Bundle();
        args.putParcelable("data", photo);
        AddBodyLocationDialogFragment fragment = new AddBodyLocationDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bodyPhotoSrc = getArguments().getParcelable("data");
        bodyPhotoBitmap = ImageConverter.base64DecodeBitmap(bodyPhotoSrc.getBlob(), ImageConverter.createMutableBitmap());
        canvas = new Canvas(bodyPhotoBitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);

        listener = (AddBodyLocationDialogListener) getActivity();
        location = new BodyLocation(bodyPhotoSrc.getId());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_body_location, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Path point = new Path();

        bodyPhotoView = view.findViewById(R.id.fragment_body_location_image);
        bodyLocationCanvas = view.findViewById(R.id.fragment_body_location_canvas);
        saveBodyLocationButton =  view.findViewById(R.id.fragment_submit_body_location);

        bodyPhotoView.setImageBitmap(bodyPhotoSrc.getPhoto());
        bodyLocationCanvas.setImageBitmap(bodyPhotoBitmap);

        bodyPhotoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                switch(e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // we have placed a point on the canvas
                        dirty = true;
                        // we have to scale from phone view dimensions to pixels on canvas
                        float canvasX = canvas.getWidth() * (e.getX()/view.getWidth());
                        float canvasY = canvas.getHeight() * (e.getY()/view.getHeight());

                        // update body location
                        location.setLocation(canvasX, canvasY);

                        drawPoint(canvasX, canvasY);
                        view.invalidate();
                        return true;
                }
                return false;
            }
        });

        saveBodyLocationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (dirty) {
                    listener.onFinishAddBodyLocation(location, saveBitmapImage());
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Place a point on the image!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Bitmap saveBitmapImage() {
        Bitmap res = bodyPhotoSrc.getPhoto().copy(Bitmap.Config.ARGB_8888, true);
        Canvas overlay = new Canvas(res);
        overlay.drawCircle(location.getX(), location.getY(),5, paint);
        return res;
    }

    private void drawPoint(float x, float y) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawCircle(x, y, 5, paint);
    }

}
