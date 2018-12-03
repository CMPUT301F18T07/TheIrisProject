/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.team7.cmput301.android.theirisproject.Extras;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.model.GeoLocation;

import java.util.ArrayList;
import java.util.Locale;

/**
 * MapActivity is used to display a map, letting the user add a marker for a geolocation (when called by
 * AddRecordActivity), or see the location(s) of record(s) (when called by ViewProblemActivity or
 * ViewRecordActivity. Referenced https://developers.google.com/maps/documentation/android-sdk/start
 * for how to create and set up map, get permissions and device location
 * @author VinnyLuu
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback, AddGeoLocationDialogFragment.AddGeoLocationDialogListener {

    private GoogleMap mMap;
    private ArrayList<GeoLocation> locations;
    private ArrayList<String> titles;
    private LatLng selectedLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Boolean mLocationPermissionGranted = false;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private static final float DEFAULT_ZOOM = 15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        try {
            Log.d("LOCATIONS", "YES LOCATIONS");
            locations = (ArrayList<GeoLocation>) getIntent().getSerializableExtra(Extras.EXTRA_LOCATION);
            titles = getIntent().getStringArrayListExtra(Extras.EXTRA_TITLES);
        } catch (NullPointerException n) {
            Log.d("LOCATIONS", "NO LOCATIONS");
        }
        getLocationPermission();

    }

    /**
     * Initializes the map fragment and when done, will call on onMapReady
     */
    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    /**
     * If the permissions have been granted, will get the device's location and move camera to that
     * location. Is called only when there are no locations passed into the activity, setting the
     * default start location of the map to the device's location (i.e when adding a geolocation
     * to a new record)
     */
    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {

                            Location currentLocation = (Location) task.getResult();
                            LatLng latLng;

                            if (currentLocation == null) {
                                // set to Mountain View
                                latLng = new LatLng(37.4220, -122.0841);
                            } else {
                                latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            }
                            moveCamera(latLng, DEFAULT_ZOOM);

                        } else {
                            Toast.makeText(MapActivity.this, R.string.map_error, Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        } catch (SecurityException e) {
            finish();
        }
    }

    /**
     * called on to move the position of camera to specified location and zoom
     * @param latLng
     * @param zoom
     */
    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    /**
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            initMap();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the results of the request for location permissions
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        /** Check whether there is no location passed (i.e the user is adding geolocation to record)
         * or if there are locations (i.e the user is viewing a records location)
         */
        mMap = googleMap;
        if (locations == null || locations.isEmpty()) {
            // No location entered, the user wants to add a location, set default to current pos
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    mMap.clear();
                    String markerDescription = String.format(Locale.getDefault(),
                            "Lat: %1$.5f, Long: %2$.5f",
                            latLng.latitude,
                            latLng.longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng)
                            .title(getString(R.string.selected_location))
                            .snippet(markerDescription));
                    moveCamera(latLng, DEFAULT_ZOOM);
                    selectedLocation = latLng;
                    Log.d("SET LOCATION", selectedLocation.toString());
                    DialogFragment addGeoLocationDialogFragment = new AddGeoLocationDialogFragment();
                    addGeoLocationDialogFragment.show(getFragmentManager(), AddGeoLocationDialogFragment.class.getSimpleName());
                }
            });
        }
        else {
            // There are locations passed into the activity, set markers for all of them
            LatLng coordinates = null;
            for (int i = 0; i < locations.size(); i++) {
                double[] pos = locations.get(i).asDouble();
                coordinates = new LatLng(pos[0], pos[1]);
                String markerDescription = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f",
                        coordinates.latitude,
                        coordinates.longitude);
                mMap.addMarker(new MarkerOptions().position(coordinates)
                        .title(titles.get(i))
                        .snippet(markerDescription));
            }
            moveCamera(coordinates, DEFAULT_ZOOM);
        }
    }

    /**
     * Overridden method from AddGeoLocationDialogFragment that has the result of whether the user
     * wants to confirm the selected location as the geolocation for the record.
     * @param success
     */
    @Override
    public void onFinishGeoLocation(boolean success) {
        if (success) {
            Log.d("DONE LOCATION", selectedLocation.toString());
            Intent intent = new Intent();
            double location[] = {selectedLocation.latitude, selectedLocation.longitude};
            intent.putExtra(Extras.EXTRA_LOCATION, location);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
