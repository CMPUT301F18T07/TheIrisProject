package com.team7.cmput301.android.theirisproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.team7.cmput301.android.theirisproject.R;
import com.team7.cmput301.android.theirisproject.model.GeoLocation;


import java.util.ArrayList;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<GeoLocation> locations;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        try {
            locations = (ArrayList<GeoLocation>) getIntent().getSerializableExtra("location");
            titles = getIntent().getStringArrayListExtra("titles");
        } catch (NullPointerException n) {
            setDeviceLocation();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setDeviceLocation() {
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /** Check whether there is no location passed (i.e the user is adding geolocation to record)
         * or if there are locations (i.e the user is viewing a records location
         */
        if (locations == null) {
            // No location entered, the user wants to add a location, set default to current pos
            LatLng pos = new LatLng(-34, 151);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
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
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    confirmLocation(latLng);
                }
            });
        }
        else {
            // There are locations passed into the activity, set markers for all of them
            LatLng coordinates = null;
            for (int i = 0; i < locations.size(); i++) {
                double[] pos = locations.get(i).asDouble();
                coordinates = new LatLng(pos[0], pos[1]);
                mMap.addMarker(new MarkerOptions().position(coordinates).title(titles.get(i)));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));

        }

    }

    public void confirmLocation(LatLng latLng) {
        Intent intent = new Intent();
        double location[] = {latLng.latitude, latLng.longitude};
        // TODO: make extra name
        intent.putExtra("location", location);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
