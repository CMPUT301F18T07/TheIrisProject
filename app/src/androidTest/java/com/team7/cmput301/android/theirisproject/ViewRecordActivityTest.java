package com.team7.cmput301.android.theirisproject;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.team7.cmput301.android.theirisproject.activity.ViewRecordActivity;
import com.team7.cmput301.android.theirisproject.helper.Timer;
import com.team7.cmput301.android.theirisproject.model.GeoLocation;
import com.team7.cmput301.android.theirisproject.model.Record;
import com.team7.cmput301.android.theirisproject.model.RecordPhoto;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * LoginActivityTest contains UI testing pertaining to the login screen
 *
 * @author jtwong
 * @see ViewRecordActivity
 */
public class ViewRecordActivityTest extends ActivityInstrumentationTestCase2<ViewRecordActivity> {

    private Solo solo;
    private String problemId = "mwmwmw";
    private String title = "Title";
    private String text = "Text";
    private Date date = new Date();
    private GeoLocation geoLocation = new GeoLocation(1.0, 1.0);
    private List<RecordPhoto> recordPhotos = new ArrayList<>();

    public ViewRecordActivityTest() {
        super(ViewRecordActivity.class);
    }

    @Override
    protected void setUp() {
        Record record = new Record(problemId, title, text, date, geoLocation, recordPhotos);
        Intent intent = new Intent();
        intent.putExtra("record_id", "AWclWyL9f4yBogDtdu_W");
        setActivityIntent(intent);
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void testActivity() {
        String activityName = ViewRecordActivity.class.getSimpleName();
        solo.assertCurrentActivity("Wrong activity found, should be " + activityName, activityName);
    }

    /**
     * Checking to see if the record data is correctly displayed
     */
    @Test
    public void testCorrectRecordData() {
        // Wait for activity to get Record information from db
        Timer.sleep(1000);

        TextView recordTitle = (TextView) solo.getView(R.id.record_title);
        TextView recordDescription = (TextView) solo.getView(R.id.record_description);
        TextView recordDate = (TextView) solo.getView(R.id.record_date);
        TextView recordGeoLocation = (TextView) solo.getView(R.id.record_geo_location);

        assertTrue(recordTitle.getText().toString().equals(title));
        assertTrue(recordDescription.getText().toString().equals(text));
        assertTrue(recordDate.getText().toString().equals(date.toString()));
        assertTrue(recordGeoLocation.getText().toString().equals(geoLocation.toString()));
    }

}
