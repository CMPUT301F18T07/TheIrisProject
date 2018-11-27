package com.team7.cmput301.android.theirisproject.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Dialog fragment that confirms with the user if the selected location is the location to set
 * for the record
 *
 * @author VinnyLuu
 */
public class AddGeoLocationDialogFragment extends DialogFragment {


    private AddGeoLocationDialogListener listener;

    // Interface that communicates with calling activity on the option the user has selected
    public interface AddGeoLocationDialogListener {
        void onFinishGeoLocation(boolean success);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        listener = (AddGeoLocationDialogListener) getActivity();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Add GeoLocation")
        .setMessage("Do you want to add this location?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onFinishGeoLocation(true);
                dialog.dismiss();
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onFinishGeoLocation(false);
                dialog.dismiss();
            }
        }).create();
        return alertDialogBuilder.create();
    }
}
