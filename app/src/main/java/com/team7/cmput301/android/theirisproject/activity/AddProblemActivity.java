package com.team7.cmput301.android.theirisproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.team7.cmput301.android.theirisproject.BodyPhotoListAdapter;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.controller.AddProblemController;
import com.team7.cmput301.android.theirisproject.task.Callback;


/**
 * AddProblemActivity is a form to add an activity,
 * by click on submit we are sending a Problem object
 * to our database to be stored (handle offline by storing it in local json).
 *
 * @author itstc
 * */
public class AddProblemActivity extends IrisActivity {
    private static final int REQUEST_CAMERA_IMAGE = 1;

    private AddProblemController controller;
    private TextView name;
    private TextView desc;
    private FloatingActionButton cameraButton;
    private FloatingActionButton albumButton;

    private RecyclerView imageList;
    private BodyPhotoListAdapter bodyPhotoListAdapter;
    private RecyclerView.LayoutManager imageListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        controller = createController(getIntent());
        name = findViewById(R.id.problem_title_edit_text);
        desc = findViewById(R.id.problem_description_edit_text);

        cameraButton = findViewById(R.id.problem_camera_edit_button);
        albumButton = findViewById(R.id.problem_album_edit_button);

        // Body Photo list
        imageList = findViewById(R.id.problem_image_edit_list);
        imageListLayout = new LinearLayoutManager(this);
        ((LinearLayoutManager) imageListLayout).setOrientation(LinearLayoutManager.HORIZONTAL);
        imageList.setLayoutManager(imageListLayout);
        bodyPhotoListAdapter = new BodyPhotoListAdapter(controller.getBodyPhotos(), true);
        imageList.setAdapter(bodyPhotoListAdapter);

        // set click listener to submit button
        findViewById(R.id.problem_submit_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // send new problem to database, callback has result true if successful, else false
                controller.submitProblem(name.getText().toString(), desc.getText().toString(), new Callback<String>() {
                    @Override
                    public void onComplete(String id) {
                        if (id != null) {
                            // end Activity returning to ProblemListActivity
                            Intent intent = new Intent(AddProblemActivity.this, ViewProblemActivity.class);
                            intent.putExtra(ViewProblemActivity.EXTRA_PROBLEM_ID, id);
                            Toast.makeText(AddProblemActivity.this, "New Problem Created!", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddProblemActivity.this, "Uh oh something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchCameraIntent();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            controller.addBodyPhoto(imageBitmap);
        }
    }


    /**
     * dispatchCameraIntent will start the camera app to take a picture
     * if the patient wants to add a body photo
     * */
    private void dispatchCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA_IMAGE);
        }
    }


    @Override
    protected AddProblemController createController(Intent intent) {
        return new AddProblemController(intent);
    }

}
