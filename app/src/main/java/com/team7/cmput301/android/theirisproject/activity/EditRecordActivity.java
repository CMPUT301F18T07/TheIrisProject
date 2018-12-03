/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.ImageConverter;
import com.team7.cmput301.android.theirisproject.ImageListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.EditRecordController;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;
import com.team7.cmput301.android.theirisproject.task.Callback;

/**
 * Activity for editing a single Record. Uses the same form as AddRecordActivity.
 *
 * @author jtfwong
 * @author anticobalt
 * @see Record
 * @see AddRecordActivity
 */
public class EditRecordActivity extends IrisActivity<Record>{

    private static final int REQUEST_CAMERA_IMAGE = 1;
    private static final int REQUEST_MAP_LOCATION = 2;
    private static final int REQUEST_BODY_LOCATION = 3;

    private EditRecordController controller;
    private ImageListAdapter<RecordPhoto> recordPhotoImageListAdapter;

    private EditText titleEditText;
    private EditText descEditText;
    private Button submitButton;
    private Button bodyLocationButton;
    private FloatingActionButton cameraButton;
    private FloatingActionButton mapButton;

    private ImageView bodyLocationImage;
    private RecyclerView recordPhotoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_record);

        // Set page
        initViews();
        initAdapters();

    }

    private void initAdapters() {
        recordPhotoImageListAdapter = new ImageListAdapter<>(this, controller.getRecordPhotos(), true);
        recordPhotoListView.setAdapter(recordPhotoImageListAdapter);
        recordPhotoListView.setLayoutManager(new LinearLayoutManager(this));
        ((LinearLayoutManager)recordPhotoListView.getLayoutManager()).setOrientation(LinearLayoutManager.HORIZONTAL);
    }

    private void initViews() {

        titleEditText = findViewById(R.id.record_title_edit_text);
        descEditText = findViewById(R.id.record_description_edit_text);

        submitButton = findViewById(R.id.record_submit_button);
        bodyLocationButton = findViewById(R.id.record_body_location_button);
        cameraButton = findViewById(R.id.record_camera_button);
        mapButton = findViewById(R.id.record_map_button);

        bodyLocationImage = findViewById(R.id.record_body_location_image);
        recordPhotoListView = findViewById(R.id.record_add_image_list);

        // put Record attributes in Views
        controller = createController(getIntent());
        titleEditText.setText(controller.getRecordTitle());
        descEditText.setText(controller.getRecordDesc());
        Bitmap bodyLocation = controller.getBodyPhotoBitmap();
        if (bodyLocation != null) bodyLocationImage.setImageBitmap(bodyLocation);

        setOnClickListeners();

    }

    private void setOnClickListeners() {

        Callback contCallback = new Callback<Boolean>() {
            @Override
            public void onComplete(Boolean success) {
                if (success) {
                    finish();
                } else {
                    Toast.makeText(EditRecordActivity.this, R.string.generic_server_error, Toast.LENGTH_SHORT).show();
                }
            }
        };

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleEditText.getText().toString();
                String desc = descEditText.getText().toString();

                Toast missingFieldsToast = Toast.makeText(
                        EditRecordActivity.this, R.string.register_incomplete, Toast.LENGTH_SHORT);

                // check that all fields filled, then update Record locally and online (if possible)
                if (title.isEmpty()) {
                    missingFieldsToast.show();
                } else {
                    Boolean online = controller.submitRecord(contCallback, title, desc);
                    if (!online) {
                        showOfflineUploadToast(EditRecordActivity.this);
                    }
                    finish();
                }

            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller.getRecordPhotos().size() < 10) dispatchCameraIntent();
                else setErrorMessage(R.string.max_record_photo_message);

            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditRecordActivity.this, MapActivity.class);
                startActivityForResult(intent, REQUEST_MAP_LOCATION);
            }
        });

        bodyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditRecordActivity.this, BodyPhotoListActivity.class);
                intent.putExtra(Extras.EXTRA_BODYPHOTO_FORM, true);
                intent.putExtra(Extras.EXTRA_BODYPHOTO_USER, controller.getUserId());
                startActivityForResult(intent, REQUEST_BODY_LOCATION);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA_IMAGE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            controller.addRecordPhoto(imageBitmap);
            recordPhotoImageListAdapter.notifyDataSetChanged();

        } else if (requestCode == REQUEST_BODY_LOCATION && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap bp = ImageConverter.scaleBitmapPhoto((Bitmap) extras.get("data_img"), 512, 512);
            controller.setBodyLocation((String) extras.get("data_src"), (float[]) extras.get("data_xy"));
            bodyLocationImage.setImageBitmap(bp);

        } else if (requestCode == REQUEST_MAP_LOCATION && resultCode == RESULT_OK) {

            double location[] = data.getDoubleArrayExtra(Extras.EXTRA_LOCATION);
            controller.setGeoLocation(location);

        }

    }

    @Override
    protected EditRecordController createController(Intent intent) {
        return new EditRecordController(intent);
    }

    private void dispatchCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA_IMAGE);
        }
    }

    private void setErrorMessage(int messageResource) {
        Toast.makeText(EditRecordActivity.this, messageResource, Toast.LENGTH_SHORT).show();
    }
}
