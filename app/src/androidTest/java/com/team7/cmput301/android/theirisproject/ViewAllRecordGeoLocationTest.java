package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.MapActivity;
import com.team7.cmput301.android.theirisproject.model.GeoLocation;

import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewAllRecordGeoLocationTest extends ActivityInstrumentationTestCase2<MapActivity> {

    private Solo solo;
    private GeoLocation geoLocation1 = new GeoLocation(0.000, 1.0000);
    private GeoLocation geoLocation2 = new GeoLocation(35.00, 36.000);
    private List<Object> geoList = new ArrayList<Object>();
    private List<String> titleList = new ArrayList<String>();
    private String title1 = "bleeding";
    private String title2 = "sneezing";

    public ViewAllRecordGeoLocationTest() {
        super(MapActivity.class);
    }

    @Override
    protected void setUp() {
        geoList.add(geoLocation1);
        geoList.add(geoLocation2);
        titleList.add(title1);
        titleList.add(title2);
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_LOCATION, (Serializable) geoList);
        intent.putStringArrayListExtra(Extras.EXTRA_TITLES, (ArrayList<String>) titleList);
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void testActivity() {
        String activityName = MapActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + activityName, activityName);
    }

    @Test
    public void testAllGeoLocations() {

    }

}
